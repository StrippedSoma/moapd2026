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
package dk.itu.moapd.androidsensors.ui.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dk.itu.moapd.androidsensors.sensors.SensorCategory
import kotlinx.coroutines.launch

/**
 * Displays one sensor category with a tab row and horizontally paged pages.
 *
 * @param category The currently selected sensor category.
 * @param savedPage The externally hoisted page state shared across configuration changes.
 * @param modifier The modifier applied to the content.
 */
@Composable
fun SensorCategoryContent(
    category: SensorCategory,
    savedPage: MutableState<Int>,
    modifier: Modifier = Modifier,
) {
    val pageCount = category.pages.size
    val maxIndex = (pageCount - 1).coerceAtLeast(0)
    val initialPage = savedPage.value.coerceIn(0, maxIndex)
    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { pageCount })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pageCount) {
        if (savedPage.value > maxIndex) {
            savedPage.value = maxIndex
        }
        pagerState.scrollToPage(savedPage.value)
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            savedPage.value = page
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        val selectedIndexForRow = savedPage.value.coerceIn(0, maxIndex)
        PrimaryScrollableTabRow(selectedTabIndex = selectedIndexForRow) {
            category.pages.forEachIndexed { index, spec ->
                Tab(
                    selected = selectedIndexForRow == index,
                    onClick = {
                        savedPage.value = index
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(stringResource(spec.titleRes)) },
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            SensorPage(spec = category.pages[page])
        }
    }
}
