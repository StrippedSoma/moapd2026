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
package dk.itu.moapd.chatbluetooth.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import dk.itu.moapd.chatbluetooth.data.model.ChatMessage
import dk.itu.moapd.chatbluetooth.databinding.ItemChatMessageBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * RecyclerView adapter for displaying chat messages.
 *
 * Each message is shown as a "bubble". Sent messages are aligned to the end (right) and received
 * messages are aligned to the start (left). The visual distinction is achieved through different
 * background tints and margins.
 *
 * @see ChatMessage
 */
class ChatMessageAdapter : ListAdapter<ChatMessage, ChatMessageAdapter.MessageViewHolder>(MessageDiffCallback) {
    /**
     * A set of static attributes used in this class.
     */
    companion object MessageDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        /**
         * Called to check whether two objects represent the same item.
         *
         * For example, if your items have unique ids, this method should check their id equality.
         *
         * Note: `null` items in the list are assumed to be the same as another `null` item and are
         * assumed to not be the same as a non-`null` item. This callback will not be invoked for
         * either of those cases.
         *
         * @param oldItem The item in the old list.
         * @param newItem The item in the new list.
         *
         * @return True if the two items represent the same object or false if they are different.
         */
        override fun areItemsTheSame(
            oldItem: ChatMessage,
            newItem: ChatMessage,
        ): Boolean = oldItem.timestamp == newItem.timestamp && oldItem.text == newItem.text

        /**
         * Called to check whether two items have the same data.
         *
         * This information is used to detect if the contents of an item have changed.
         *
         * This method to check equality instead of `Object.equals(Object)` so that you can change
         * its behavior depending on your UI.
         *
         * For example, if you are using DiffUtil with a `RecyclerView.Adapter`, you should return
         * whether the items' visual representations are the same.
         *
         * This method is called only if `areItemsTheSame(T, T)` returns `true` for these items.
         *
         * Note: Two `null` items are assumed to represent the same contents. This callback will not
         * be invoked for this case.
         *
         * @param oldItem The item in the old list.
         * @param newItem The item in the new list.
         *
         * @return True if the contents of the items are the same or false if they are different.
         */
        override fun areContentsTheSame(
            oldItem: ChatMessage,
            newItem: ChatMessage,
        ): Boolean = oldItem == newItem

        /**
         * Largest margin used for the bubble.
         */
        private const val BUBBLE_LARGE_MARGIN = 128

        /**
         * Smallest margin used for the bubble.
         */
        private const val BUBBLE_SMALL_MARGIN = 64
    }

    /**
     * Called when the `RecyclerView` needs a new `ViewHolder` of the given type to represent an
     * item.
     *
     * This new `ViewHolder` should be constructed with a new `View` that can represent the items of
     * the given type. You can either create a new `View` manually or inflate it from an XML layout
     * file.
     *
     * The new `ViewHolder` will be used to display items of the adapter using
     * `onBindViewHolder(ViewHolder, int, List)`. Since it will be re-used to display different
     * items in the data set, it is a good idea to cache references to sub views of the `View` to
     * avoid unnecessary `findViewById(int)` calls.
     *
     * @param parent The `ViewGroup` into which the new `View` will be added after it is bound to an
     *      adapter position.
     * @param viewType The view type of the new `View`.
     *
     * @return A new `ViewHolder` that holds a View of the given view type.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MessageViewHolder =
        ItemChatMessageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::MessageViewHolder)

    /**
     * Called by the `RecyclerView` to display the data at the specified position. This method
     * should update the contents of the `itemView()` to reflect the item at the given position.
     *
     * Note that unlike `ListView`, `RecyclerView` will not call this method again if the position
     * of the item changes in the data set unless the item itself is invalidated or the new position
     * cannot be determined. For this reason, you should only use the `position` parameter while
     * acquiring the related data item inside this method and should not keep a copy of it. If you
     * need the position of an item later on (e.g., in a click listener), use
     * `getBindingAdapterPosition()` which will have the updated adapter position.
     *
     * Override `onBindViewHolder(ViewHolder, int, List)` instead if Adapter can handle efficient
     * partial bind.
     *
     * @param holder The `ViewHolder` which should be updated to represent the contents of the item
     *      at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(
        holder: MessageViewHolder,
        position: Int,
    ) {
        getItem(position).let(holder::bind)
    }

    /**
     * An internal view holder class used to represent the layout that shows a single `ChatMessage`
     * instance in the `RecyclerView`.
     *
     * @param binding The [ItemChatMessageBinding] instance to access the views.
     */
    class MessageViewHolder(
        private val binding: ItemChatMessageBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * The time format used to display the message timestamp.
         */
        private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        /**
         * This method binds the `MessageViewHolder` instance and more cleanly separate concerns
         * between the view holder and the adapter.
         *
         * @param message The current `ChatMessage` object.
         */
        fun bind(message: ChatMessage) {
            with(binding) {
                textMessage.text = message.text
                textTimestamp.text = timeFormat.format(Date(message.timestamp))

                val params = cardMessage.layoutParams as ConstraintLayout.LayoutParams

                if (message.isSentByMe) {
                    // Sent: push bubble to the right with a large start margin.
                    params.marginStart = BUBBLE_LARGE_MARGIN
                    params.marginEnd = BUBBLE_SMALL_MARGIN
                    params.horizontalBias = 1f

                    cardMessage.setCardBackgroundColor(
                        MaterialColors.getColor(
                            cardMessage,
                            android.R.attr.colorPrimary,
                        ),
                    )
                    textMessage.setTextColor(
                        MaterialColors.getColor(
                            cardMessage,
                            com.google.android.material.R.attr.colorOnPrimary,
                        ),
                    )
                    textTimestamp.setTextColor(
                        ColorUtils.setAlphaComponent(
                            MaterialColors.getColor(
                                cardMessage,
                                com.google.android.material.R.attr.colorOnPrimary,
                            ),
                            (0.6f * 255).toInt(),
                        ),
                    )
                } else {
                    // Received: push bubble to the left with a large end margin.
                    params.marginStart = BUBBLE_SMALL_MARGIN
                    params.marginEnd = BUBBLE_LARGE_MARGIN
                    params.horizontalBias = 0f

                    cardMessage.setCardBackgroundColor(
                        MaterialColors.getColor(
                            cardMessage,
                            com.google.android.material.R.attr.colorSurfaceVariant,
                        ),
                    )
                    textMessage.setTextColor(
                        MaterialColors.getColor(
                            cardMessage,
                            com.google.android.material.R.attr.colorOnSurfaceVariant,
                        ),
                    )
                    textTimestamp.setTextColor(
                        ColorUtils.setAlphaComponent(
                            MaterialColors.getColor(
                                cardMessage,
                                com.google.android.material.R.attr.colorOnSurfaceVariant,
                            ),
                            (0.6f * 255).toInt(),
                        ),
                    )
                }
                cardMessage.layoutParams = params
            }
        }
    }
}
