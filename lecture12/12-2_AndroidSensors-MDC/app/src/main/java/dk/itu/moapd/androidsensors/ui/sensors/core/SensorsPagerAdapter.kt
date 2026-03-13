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
package dk.itu.moapd.androidsensors.ui.sensors.core

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * A pager adapter that creates sensor pages from a compact list of [SensorPageSpec] objects.
 *
 * This adapter replaces the three category-specific adapters from the original implementation and
 * therefore keeps the project smaller and easier to maintain.
 *
 * @param fragment The parent fragment hosting the view pager.
 * @param pages The ordered list of sensor page specifications displayed by the pager.
 */
class SensorsPagerAdapter(
    fragment: Fragment,
    private val pages: List<SensorPageSpec>,
) : FragmentStateAdapter(fragment) {
    /**
     * Returns the number of pages shown by the pager.
     */
    override fun getItemCount(): Int = pages.size

    /**
     * Creates a [SensorPageFragment] for the requested position.
     *
     * @param position The index of the page to create.
     *
     * @return A fully configured [SensorPageFragment].
     */
    override fun createFragment(position: Int): Fragment = SensorPageFragment.newInstance(pages[position].key)
}
