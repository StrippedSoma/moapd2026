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
package dk.itu.moapd.androidsensors.ui.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import dk.itu.moapd.androidsensors.sensors.SensorCategory
import dk.itu.moapd.androidsensors.ui.main.components.SensorCategoryContent

/**
 * The root Compose screen that reproduces the original bottom navigation and tab pager layout.
 */
@Composable
fun MainScreen() {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var selectedCategory by rememberSaveable { mutableStateOf(SensorCategory.Motion) }
    val savedPage = rememberSaveable { mutableIntStateOf(0) }

    if (isLandscape) {
        Row(modifier = Modifier.fillMaxSize()) {
            NavigationRail {
                SensorCategory.entries.forEach { category ->
                    NavigationRailItem(
                        selected = selectedCategory == category,
                        onClick = {
                            selectedCategory = category
                            savedPage.intValue = 0
                        },
                        icon = {
                            Icon(
                                imageVector = category.imageVector,
                                contentDescription = stringResource(category.titleRes),
                            )
                        },
                        label = { Text(stringResource(category.titleRes)) },
                    )
                }
            }
            SensorCategoryContent(
                category = selectedCategory,
                savedPage = savedPage,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding(),
            )
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            SensorCategoryContent(
                category = selectedCategory,
                savedPage = savedPage,
                modifier =
                    Modifier
                        .weight(1f)
                        .statusBarsPadding(),
            )
            NavigationBar {
                SensorCategory.entries.forEach { category ->
                    NavigationBarItem(
                        selected = selectedCategory == category,
                        onClick = {
                            selectedCategory = category
                            savedPage.intValue = 0
                        },
                        icon = {
                            Icon(
                                imageVector = category.imageVector,
                                contentDescription = stringResource(category.titleRes),
                            )
                        },
                        label = { Text(stringResource(category.titleRes)) },
                    )
                }
            }
        }
    }
}
