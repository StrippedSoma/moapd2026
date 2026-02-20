/*
 * MIT License
 *
 * Copyright (c) 2026 Fabricio Batista Narcizo
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
package dk.itu.moapd.mlkit.domain.vision

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import dk.itu.moapd.mlkit.domain.model.DetectionLabel
import kotlin.math.max

/**
 * Runs ML Kit Object Detection and draws the result on top of the input image.
 *
 * @param maxFontSizePx The maximum font size of the resulting text.
 */
class ObjectDetectionProcessor(
    private val maxFontSizePx: Float = 96f,
) {

    /**
     * ML Kit Object Detection model.
     */
    private val detector by lazy {
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects()
            .enableClassification()
            .build()
        ObjectDetection.getClient(options)
    }

    /**
     * Process the input image.
     *
     * @param bitmap The input image.
     * @param onSuccess Callback for successful processing.
     * @param onFailure Callback for failed processing.
     */
    fun process(
        bitmap: Bitmap,
        onSuccess: (visualized: Bitmap) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        val image = InputImage.fromBitmap(bitmap, /* rotationDegrees = */ 0)

        detector.process(image)
            .addOnSuccessListener { results ->
                val labels = results.map { obj ->
                    val text = obj.labels.firstOrNull()?.let { label ->
                        "${label.text}, ${(label.confidence * 100).toInt()}%"
                    } ?: "Unknown"
                    DetectionLabel(obj.boundingBox, text)
                }

                onSuccess(drawDetections(bitmap, labels))
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    /**
     * Draw the detected objects on the input image.
     *
     * @param bitmap The input image.
     * @param detections The detected objects.
     *
     * @return The input image with the detected objects drawn on top.
     */
    private fun drawDetections(bitmap: Bitmap, detections: List<DetectionLabel>): Bitmap {
        val output = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(output)

        val pen = Paint().apply {
            textAlign = Paint.Align.LEFT
        }

        detections.forEach { detection ->
            drawBoundingBox(canvas, pen, detection.boundingBox)
            drawLabel(canvas, pen, detection.boundingBox, detection.text)
        }

        return output
    }

    /**
     * Draw the bounding box on the canvas.
     *
     * @param canvas The canvas to draw on.
     * @param pen The paint used to draw the bounding box.
     * @param box The bounding box to draw.
     */
    private fun drawBoundingBox(canvas: Canvas, pen: Paint, box: Rect) {
        pen.color = Color.RED
        pen.strokeWidth = 8f
        pen.style = Paint.Style.STROKE
        canvas.drawRect(box, pen)
    }

    /**
     * Draw the label on the canvas.
     *
     * @param canvas The canvas to draw on.
     * @param pen The paint used to draw the label.
     * @param box The bounding box of the label.
     * @param text The text to draw.
     */
    private fun drawLabel(canvas: Canvas, pen: Paint, box: Rect, text: String) {
        val textBounds = Rect()

        pen.style = Paint.Style.FILL_AND_STROKE
        pen.color = Color.YELLOW
        pen.strokeWidth = 2f
        pen.textSize = maxFontSizePx

        pen.getTextBounds(text, 0, text.length, textBounds)
        if (textBounds.width() == 0) return

        // Scale down to fit inside the bounding box width.
        val scaledSize = pen.textSize * box.width().toFloat() / textBounds.width().toFloat()
        pen.textSize = minOf(pen.textSize, scaledSize)

        // Recompute bounds after resizing.
        pen.getTextBounds(text, 0, text.length, textBounds)

        val margin = max(0f, (box.width() - textBounds.width()) / 2f)
        val x = box.left + margin
        val y = box.top + textBounds.height().toFloat()
        canvas.drawText(text, x, y, pen)
    }
}
