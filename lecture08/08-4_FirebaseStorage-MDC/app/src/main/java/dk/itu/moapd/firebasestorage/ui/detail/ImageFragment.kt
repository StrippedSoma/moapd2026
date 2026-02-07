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
package dk.itu.moapd.firebasestorage.ui.detail
import dk.itu.moapd.firebasestorage.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.findNavController
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dk.itu.moapd.firebasestorage.core.FirebaseConfig.BUCKET_URL
import dk.itu.moapd.firebasestorage.databinding.FragmentImageBinding
import dk.itu.moapd.firebasestorage.domain.model.Image
import dk.itu.moapd.firebasestorage.ui.utils.NavigationArgs
import dk.itu.moapd.firebasestorage.ui.utils.viewBinding

/**
 * An Fragment class with methods to manage the image Fragment of Firebase Storage application.
 */
class ImageFragment : Fragment(R.layout.fragment_image) {

    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private val binding by viewBinding(FragmentImageBinding::bind)

    /**
     * Called immediately after `onCreateView(LayoutInflater, ViewGroup, Bundle)` has returned, but
     * before any saved state has been restored in to the view. This gives subclasses a chance to
     * initialize themselves once they know their view hierarchy has been completely created. The
     * fragment's view hierarchy is not however attached to its parent at this point.
     *
     * @param view The View returned by `onCreateView(LayoutInflater, ViewGroup, Bundle)`.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *      saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displaySelectedImage()
        setupBackButtonNavigation()
    }

    /**
     * Displays the selected image by retrieving the image path from the arguments bundle. It parses
     * the JSON string representing the image information into an Image object using Gson, then
     * loads and displays the image using the provided path.
     */
    private fun displaySelectedImage() {
        arguments?.getString(NavigationArgs.ARG_IMAGE)?.let { json ->
            Gson().fromJson(json, Image::class.java).path?.also { imagePath ->
                loadImage(imagePath)
            }
        }
    }

    /**
     * Loads the image from the provided image path using Firebase Storage. Once the image is
     * successfully loaded, it is displayed in the ImageView using Picasso library. Progress bar is
     * shown while loading the image and hidden after the loading process completes, regardless of
     * success or failure.
     *
     * @param imagePath The path of the image to load.
     */
    private fun loadImage(imagePath: String) {
        binding.progressBar.visibility = View.VISIBLE
        Firebase.storage(BUCKET_URL).reference
            .child(imagePath).downloadUrl
            .addOnSuccessListener { url -> Picasso.get().load(url).into(binding.imageView) }
            .addOnCompleteListener { binding.progressBar.visibility = View.GONE }
            .addOnFailureListener { binding.progressBar.visibility = View.GONE }
    }

    /**
     * Sets up the back button navigation functionality. When the back button is clicked, it
     * navigates back to the `MainFragment` using Navigation Component.
     */
    private fun setupBackButtonNavigation() {
        binding.backButton.setOnClickListener {
            requireActivity().findNavController(R.id.fragment_container_view).navigate(
                R.id.action_image_to_main
            )
        }
    }

}
