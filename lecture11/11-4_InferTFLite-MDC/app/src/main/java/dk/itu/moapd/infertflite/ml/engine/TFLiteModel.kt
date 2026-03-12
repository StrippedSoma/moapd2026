/*
 * MIT License
 *
 * Copyright (c) 2026 Elizabete Munzlinger and Fabricio Batista Narcizo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package dk.itu.moapd.infertflite.ml.engine

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import androidx.core.graphics.createBitmap
import dk.itu.moapd.infertflite.ml.api.InferenceEngine
import dk.itu.moapd.infertflite.ml.api.TensorOutputs
import dk.itu.moapd.infertflite.ml.config.ModelConfig
import dk.itu.moapd.infertflite.ml.preprocess.BitmapRgbFloatPreprocessor
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.gpu.GpuDelegate
import org.tensorflow.lite.nnapi.NnApiDelegate
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.Closeable
import java.nio.channels.FileChannel

/**
 * Runtime options for TensorFlow Lite inference.
 */
enum class TFLiteRuntime {
    CPU,
    GPU,
    NNAPI,
}

/**
 * A class for managing TensorFlow Lite models.
 *
 * This class initializes and handles a TensorFlow Lite interpreter, loads the model from assets,
 * prepares input/output tensors, preprocesses bitmaps, performs warmup, and provides safe
 * inference execution with recovery behavior after runtime failures.
 *
 * @param application The application context used for accessing assets and other system resources.
 * @param config The configuration details for the model, including file name, input/output layer
 *      names, and input dimensions.
 * @param runtime The preferred TensorFlow Lite runtime backend.
 * @param numThreads Number of CPU threads used when runtime is [TFLiteRuntime.NNAPI].
 */
class TFLiteModel(
    private val application: Application,
    private val config: ModelConfig,
    private val runtime: TFLiteRuntime = TFLiteRuntime.NNAPI,
    private val numThreads: Int = 4,
) : Closeable,
    InferenceEngine<TensorOutputs> {
    /**
     * A set of private constants used in this class.
     */
    companion object {
        private val TAG = TFLiteModel::class.qualifiedName

        /**
         * Maximum number of initialization retry attempts.
         */
        private const val MAX_RETRIES = 5

        /**
         * Initial delay in milliseconds before first retry.
         */
        private const val INITIAL_DELAY_MS = 500L

        /**
         * Maximum delay in milliseconds between retries.
         */
        private const val MAX_DELAY_MS = 5000L

        /**
         * Number of color channels in RGB images.
         */
        private const val CHANNELS_RGB = 3

        /**
         * Neutral gray value for normalization.
         */
        private const val NEUTRAL_GRAY_VALUE = 0.5f
    }

    /**
     * Specification for an output tensor.
     */
    private data class OutputSpec(
        val index: Int,
        val name: String,
        val shape: IntArray,
        val dataType: DataType,
    )

    /**
     * The TensorFlow Lite interpreter instance.
     */
    private var interpreter: Interpreter? = null

    /**
     * Shape of the input tensor in NHWC format: [batch, height, width, channels].
     */
    private var inputShape: IntArray = intArrayOf()

    /**
     * The input tensor data type.
     */
    private var inputDataType: DataType? = null

    /**
     * The expected output tensors resolved from the interpreter.
     */
    private var outputSpecs: List<OutputSpec> = emptyList()

    /**
     * GPU delegate, when enabled.
     */
    private var gpuDelegate: GpuDelegate? = null

    /**
     * NNAPI delegate, when enabled.
     */
    private var nnApiDelegate: NnApiDelegate? = null

    /**
     * Utility object for bitmap-to-buffer preprocessing operations.
     */
    private val bitmapRgbFloatPreprocessor = BitmapRgbFloatPreprocessor()

    /**
     * Input width dimension.
     */
    private val inputW = config.inputNHWC[2]

    /**
     * Input height dimension.
     */
    private val inputH = config.inputNHWC[1]

    /**
     * Destination rectangle for bitmap resizing.
     */
    private val dstRect = Rect(0, 0, inputW, inputH)

    /**
     * Paint object with bitmap filtering enabled.
     */
    private val paint = Paint(Paint.FILTER_BITMAP_FLAG)

    /**
     * Reusable bitmap and canvas for input preprocessing.
     */
    private var reusableInputBitmap: Bitmap? = null

    /**
     * Reusable canvas for input preprocessing.
     */
    private var reusableCanvas: Canvas? = null

    /**
     * Flag indicating whether the model has been closed.
     */
    @Volatile
    private var isClosed: Boolean = false

    /**
     * Flag indicating whether the model has been initialized.
     */
    @Volatile
    private var isInitialized: Boolean = false

    /**
     * Mutex for thread-safe initialization.
     */
    private val initMutex = Mutex()

    /**
     * Lock object for synchronizing state changes.
     */
    private val stateLock = Any()

    /**
     * Initializes the model by loading it from assets with retry logic. This method should be
     * called before using the model for inference.
     *
     * **Thread Safety:**
     * It is safe to call this method multiple times. Subsequent calls will be ignored if the
     * model is already successfully initialized or if it has been closed.
     *
     * **Recovery After Inference Failure:**
     * This method can be called to reinitialize the model after an inference failure. When
     * [infer] encounters an exception, it releases native resources and marks the model as
     * uninitialized. Calling this method again will reload the model from assets, allowing
     * the application to recover without requiring a complete restart.
     *
     * Implements exponential backoff retry strategy to handle transient failures during model
     * loading (e.g., DSP initialization timing issues).
     *
     * @throws IllegalStateException if model loading fails after all retry attempts.
     */
    override suspend fun initialize() {
        if (isInitialized || isClosed) return

        initMutex.withLock {
            var successfullyLoaded = isInitialized || isClosed

            if (!successfullyLoaded) {
                var lastException: Exception? = null
                var currentDelay = INITIAL_DELAY_MS

                for (attempt in 1..MAX_RETRIES) {
                    try {
                        Log.d(TAG, "Attempting to load TFLite model ${config.fileName} ($attempt/$MAX_RETRIES)")

                        if (loadModel()) {
                            isInitialized = true
                            successfullyLoaded = true
                            Log.i(TAG, "TFLite model initialized successfully: ${config.fileName}")
                            break
                        }

                        Log.w(TAG, "TFLite model loading returned false (attempt $attempt)")
                    } catch (e: Exception) {
                        lastException = e
                        Log.w(TAG, "TFLite model loading exception (attempt $attempt): ${e.message}")
                    }

                    if (attempt < MAX_RETRIES && !successfullyLoaded) {
                        delay(currentDelay)
                        currentDelay = (currentDelay * 2).coerceAtMost(MAX_DELAY_MS)
                    }
                }

                if (!successfullyLoaded) {
                    val errorMsg = "Failed to load TFLite model ${config.fileName} after $MAX_RETRIES attempts"
                    Log.e(TAG, errorMsg, lastException)
                    throw IllegalStateException(errorMsg, lastException)
                }
            }
        }
    }

    /**
     * Loads the TFLite model from assets, configures the interpreter, resolves input and
     * output tensors, and performs a warmup pass.
     *
     * @return `true` if the model was successfully loaded and initialized; `false` otherwise.
     */
    private fun loadModel(): Boolean {
        disposeInterpreter()

        val model = loadModelFromAssets() ?: return false

        val modelInputShape = model.getInputTensor(0)?.shape() ?: return false
        val modelInputType = model.getInputTensor(0)?.dataType() ?: return false

        if (!modelInputShape.contentEquals(config.inputNHWC.toIntArray())) {
            Log.e(
                TAG,
                "Input shape mismatch. Model=${modelInputShape.contentToString()}, " +
                    "Config=${config.inputNHWC.toIntArray().contentToString()}",
            )
            model.close()
            return false
        }

        if (modelInputType != DataType.FLOAT32) {
            Log.e(TAG, "Only FLOAT32 input models are currently supported. Found: $modelInputType")
            model.close()
            return false
        }

        val resolvedOutputSpecs =
            resolveOutputSpecs(model) ?: run {
                model.close()
                return false
            }

        interpreter = model
        inputShape = modelInputShape
        inputDataType = modelInputType
        outputSpecs = resolvedOutputSpecs

        if (!warmup()) {
            disposeInterpreter()
            return false
        }

        return true
    }

    /**
     * Loads the TensorFlow Lite model from the application's asset folder.
     *
     * @return A configured [Interpreter] instance if loading succeeds, or `null` otherwise.
     */
    private fun loadModelFromAssets(): Interpreter? =
        try {
            val options = buildInterpreterOptions()

            application.assets.openFd(config.fileName).use { assetFd ->
                val modelBuffer =
                    assetFd.createInputStream().channel.map(
                        FileChannel.MapMode.READ_ONLY,
                        assetFd.startOffset,
                        assetFd.declaredLength,
                    )

                Interpreter(modelBuffer, options)
            }
        } catch (e: Exception) {
            Log.e(TAG, "TFLite model loading error", e)
            null
        }

    /**
     * Builds interpreter options according to the selected runtime.
     *
     * @return A configured [Interpreter.Options] instance.
     */
    private fun buildInterpreterOptions(): Interpreter.Options {
        val options = Interpreter.Options()

        when (runtime) {
            TFLiteRuntime.CPU -> {
                options.setNumThreads(numThreads)
            }

            TFLiteRuntime.GPU -> {
                val compatibilityList = CompatibilityList()
                if (compatibilityList.isDelegateSupportedOnThisDevice) {
                    gpuDelegate = GpuDelegate(compatibilityList.bestOptionsForThisDevice)
                    options.addDelegate(gpuDelegate)
                } else {
                    Log.w(TAG, "GPU delegate is not supported on this device. Falling back to CPU.")
                    options.setNumThreads(numThreads)
                }
            }

            TFLiteRuntime.NNAPI -> {
                nnApiDelegate = NnApiDelegate()
                options.addDelegate(nnApiDelegate)
            }
        }

        return options
    }

    /**
     * Resolves the output tensors from the TensorFlow Lite model.
     *
     * The output names in the config must match the actual tensor names reported by the model.
     *
     * @param model The loaded [Interpreter].
     *
     * @return A list of resolved [OutputSpec] or `null` if any output cannot be found.
     */
    private fun resolveOutputSpecs(model: Interpreter): List<OutputSpec>? {
        val availableOutputs =
            (0 until model.outputTensorCount).associateBy(
                keySelector = { index -> model.getOutputTensor(index).name() },
                valueTransform = { index -> index },
            )

        val specs = mutableListOf<OutputSpec>()

        for (name in config.outputLayerNames) {
            val index = availableOutputs[name]
            if (index == null) {
                Log.e(TAG, "Output tensor '$name' was not found in model ${config.fileName}")
                return null
            }

            val tensor = model.getOutputTensor(index)
            val shape = tensor.shape() ?: return null
            val dataType = tensor.dataType() ?: return null

            specs +=
                OutputSpec(
                    index = index,
                    name = name,
                    shape = shape,
                    dataType = dataType,
                )
        }

        return specs
    }

    /**
     * Performs a warmup run of the interpreter to initialize internal states.
     *
     * @return `true` if warmup succeeds; `false` otherwise.
     */
    private fun warmup(): Boolean =
        try {
            val size = inputW * inputH * CHANNELS_RGB
            val warm = FloatArray(size) { NEUTRAL_GRAY_VALUE }

            val inputType = inputDataType ?: return false
            val localInterpreter = interpreter ?: return false

            val inputBuffer = TensorBuffer.createFixedSize(inputShape, inputType)
            inputBuffer.loadArray(warm)

            val outputs = createOutputBuffers()
            val outputsByIndex = outputsByIndex(outputs)

            localInterpreter.runForMultipleInputsOutputs(
                arrayOf(inputBuffer.buffer),
                outputsByIndex,
            )

            true
        } catch (e: Exception) {
            Log.w(TAG, "Warmup failed for TFLite model ${config.fileName}", e)
            false
        }

    /**
     * Runs the TensorFlow Lite model on a preprocessed and resized bitmap.
     *
     * This method runs inference synchronously on the current thread.
     *
     * On failure, the model enters a recoverable state.
     *
     * @param bitmap The original input image to be processed.
     * @param block A lambda function that consumes the output tensors.
     *
     * @return The result of [block], or `null` if inference fails.
     */
    override fun <R> infer(
        bitmap: Bitmap,
        block: (TensorOutputs) -> R,
    ): R? {
        if (isClosed || !isInitialized) {
            return null
        }

        return try {
            executeInference(bitmap, block)
        } catch (e: Exception) {
            Log.e(TAG, "TFLite inference execution failed", e)

            synchronized(stateLock) {
                isInitialized = false
                disposeInterpreter()
            }

            null
        }
    }

    /**
     * Executes the actual inference operations synchronously.
     *
     * @param bitmap The input bitmap to be processed.
     * @param block A lambda function that processes the output tensors.
     *
     * @return The result of [block], or `null` if validation or inference fails.
     */
    private fun <R> executeInference(
        bitmap: Bitmap,
        block: (TensorOutputs) -> R,
    ): R? {
        val inputBmp = getReusableInput(bitmap)

        val isStateValid =
            !isClosed &&
                isInitialized &&
                inputBmp.width == inputW &&
                inputBmp.height == inputH

        if (!isStateValid) return null

        bitmapRgbFloatPreprocessor.convertBitmapToBuffer(inputBmp)
        val floats = bitmapRgbFloatPreprocessor.bufferToFloatsRGB()

        if (bitmapRgbFloatPreprocessor.wasLastBufferBlack()) {
            return null
        }

        return synchronized(stateLock) {
            if (!isClosed && isInitialized) {
                val inputType = inputDataType ?: return null
                val localInterpreter = interpreter ?: return null

                val inputBuffer = TensorBuffer.createFixedSize(inputShape, inputType)
                inputBuffer.loadArray(floats)

                val outputs = createOutputBuffers()
                val outputsByIndex = outputsByIndex(outputs)

                localInterpreter.runForMultipleInputsOutputs(
                    arrayOf(inputBuffer.buffer),
                    outputsByIndex,
                )

                block(outputs)
            } else {
                null
            }
        }
    }

    /**
     * Creates per-inference output buffers according to the resolved output specs.
     *
     * @return A map of output names to [TensorBuffer] instances.
     */
    private fun createOutputBuffers(): TensorOutputs =
        outputSpecs.associate { spec ->
            spec.name to TensorBuffer.createFixedSize(spec.shape, spec.dataType)
        }

    /**
     * Converts a name-based output map into the index-based structure.
     *
     * @param outputs A map of output names to [TensorBuffer] instances.
     *
     * @return A mutable map of output indices to [TensorBuffer] instances.
     */
    private fun outputsByIndex(outputs: TensorOutputs): MutableMap<Int, Any> =
        outputSpecs
            .associate { spec ->
                spec.index to requireNotNull(outputs[spec.name]).buffer
            }.toMutableMap()

    /**
     * Retrieves a reusable input bitmap resized to the model's input dimensions.
     *
     * @param src The source bitmap to be resized.
     *
     * @return A bitmap resized to the model's input dimensions.
     */
    private fun getReusableInput(src: Bitmap): Bitmap {
        val bmp =
            reusableInputBitmap ?: createBitmap(inputW, inputH, Bitmap.Config.ARGB_8888)
                .also {
                    reusableInputBitmap = it
                    reusableCanvas = Canvas(it)
                }

        reusableCanvas?.drawBitmap(src, null, dstRect, paint)
        return bmp
    }

    /**
     * Releases resources associated with the interpreter and delegates.
     */
    private fun disposeInterpreter() {
        interpreter?.close()
        interpreter = null

        gpuDelegate?.close()
        gpuDelegate = null

        nnApiDelegate?.close()
        nnApiDelegate = null

        inputShape = intArrayOf()
        inputDataType = null
        outputSpecs = emptyList()
    }

    /**
     * Releases resources associated with the current TensorFlow Lite model instance.
     *
     * Thread-safe.
     */
    override fun close() {
        synchronized(stateLock) {
            if (isClosed) return
            isClosed = true

            runCatching { interpreter?.close() }
            runCatching { gpuDelegate?.close() }
            runCatching { nnApiDelegate?.close() }

            gpuDelegate = null
            nnApiDelegate = null
            isInitialized = false
        }

        reusableCanvas = null
        reusableInputBitmap = null
    }
}
