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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dk.itu.moapd.chatbluetooth.data.model.BluetoothDeviceItem
import dk.itu.moapd.chatbluetooth.databinding.ItemDeviceBinding

/**
 * RecyclerView adapter for displaying Bluetooth devices.
 *
 * Used by both the Paired Devices and Discovery fragments to show a list of
 * [BluetoothDeviceItem]s. When the user taps a device, the [onDeviceClick]
 * callback is invoked.
 *
 * ## ListAdapter & DiffUtil
 * We extend [ListAdapter] instead of a plain `RecyclerView.Adapter`. ListAdapter
 * uses [DiffUtil] internally to compute the minimal set of changes when the list
 * is updated, resulting in smooth animations and better performance.
 *
 * @param onDeviceClick Callback invoked when a device item is clicked.
 */
class DeviceListAdapter(
    private val onDeviceClick: (BluetoothDeviceItem) -> Unit,
) : ListAdapter<BluetoothDeviceItem, DeviceListAdapter.DeviceViewHolder>(DeviceDiffCallback) {
    /**
     * A set of static attributes used in this class.
     */
    companion object DeviceDiffCallback : DiffUtil.ItemCallback<BluetoothDeviceItem>() {
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
            oldItem: BluetoothDeviceItem,
            newItem: BluetoothDeviceItem,
        ): Boolean = oldItem.address == newItem.address

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
            oldItem: BluetoothDeviceItem,
            newItem: BluetoothDeviceItem,
        ): Boolean = oldItem == newItem
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
    ): DeviceViewHolder =
        ItemDeviceBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::DeviceViewHolder)

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
        holder: DeviceViewHolder,
        position: Int,
    ) {
        getItem(position).let(holder::bind)
    }

    /**
     * An internal view holder class used to represent the layout that shows a single
     * `BluetoothDeviceItem` instance in the `RecyclerView`.
     *
     * @param binding The [BluetoothDeviceItem] instance to access the views.
     */
    inner class DeviceViewHolder(
        private val binding: ItemDeviceBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * This method binds the `DeviceViewHolder` instance and more cleanly separate concerns
         * between the view holder and the adapter.
         *
         * @param item The current `BluetoothDeviceItem` object.
         */
        fun bind(item: BluetoothDeviceItem) {
            binding.textDeviceName.text = item.name
            binding.textDeviceAddress.text = item.address
            binding.root.setOnClickListener {
                onDeviceClick(item)
            }
        }
    }
}
