/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.model.Stock
import com.example.androiddevchallenge.ui.theme.green
import com.example.androiddevchallenge.ui.theme.red
import com.example.androiddevchallenge.viewmodel.HomeViewModel
import java.util.Locale

@ExperimentalMaterialApi
@Composable
fun Home(isDarkTheme: Boolean = false) {

    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed)
    var selectedTab by remember { mutableStateOf(TabHeader.ACCOUNT) }
    val viewModel: HomeViewModel = viewModel()

    BackdropScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            TabBar(
                selectedTab = selectedTab,
                tabList = TabHeader.values().map { it.name },
                onTabSelected = { selectedTab = it }
            )
        },
        backLayerContent = {
            AccountScreen(homeViewModel = viewModel)
        },
        frontLayerContent = { FrontLayerScreen(isDarkTheme = isDarkTheme, homeViewModel = viewModel) },

        backLayerBackgroundColor = MaterialTheme.colors.background,
        frontLayerShape = RectangleShape,
        stickyFrontLayer = false, // Whether the front layer should stick to the height of the back layer
        peekHeight = 0.dp, // The height of the visible part of the back layer when it is concealed.
        headerHeight = 64.dp, // The minimum height of the front layer when it is inactive.
        frontLayerScrimColor = Color.Transparent // The color of the scrim applied to the front layer when the back layer is revealed. If you set this to Color.Transparent, then a scrim will not be applied and interaction with the front layer will not be blocked when the back layer is revealed.
    )
}

enum class TabHeader {
    ACCOUNT, WATCHLIST, PROFILE
}

@Composable
fun TabBar(
    selectedTab: TabHeader,
    tabList: List<String>,
    onTabSelected: (TabHeader) -> Unit
) {

    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        backgroundColor = MaterialTheme.colors.background,
        indicator = {},
        divider = {},
    ) {
        tabList.forEachIndexed { index, tabHeader ->
            val selected = selectedTab.ordinal == index

            Tab(selected = selected, onClick = { onTabSelected(TabHeader.values()[index]) }) {
                Text(
                    text = tabHeader,
                    Modifier
                        .paddingFromBaseline(64.dp)
                        .padding(bottom = 8.dp),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Composable
fun AccountScreen(homeViewModel: HomeViewModel = viewModel()) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Balance",
            modifier = Modifier.paddingFromBaseline(32.dp),
            style = MaterialTheme.typography.subtitle1
        )
        Text(text = homeViewModel.balance.toStringMoney(), style = MaterialTheme.typography.h1)

        Text(
            text = "${homeViewModel.status.toStatusString()} today",
            color = if (homeViewModel.status > 0.0) green else red,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.paddingFromBaseline(40.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { /*TODO*/ },
            shape = CircleShape,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "TRANSACT".toUpperCase(Locale.ROOT),
                style = MaterialTheme.typography.button
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp)
        ) {
            items(homeViewModel.chips) { chip ->
                Surface(
                    modifier = Modifier
                        .height(40.dp)
                        .padding(end = 8.dp)
                        .clickable { },
                    shape = CircleShape,
                    border = BorderStroke(color = MaterialTheme.colors.onBackground, width = 1.dp),
                    color = Color.Transparent,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = chip.name,
                            style = MaterialTheme.typography.body1
                        )
                        chip.icon?.let {
                            Icon(it, contentDescription = chip.name)
                        }
                    }
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_home_illos),
            contentDescription = "Graph",
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun FrontLayerScreen(isDarkTheme: Boolean = false, homeViewModel: HomeViewModel = viewModel()) {
    Surface(
        Modifier
            .background(if (isDarkTheme) MaterialTheme.colors.background else Color.White)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(
                text = "Positions",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.paddingFromBaseline(40.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn {
                items(homeViewModel.stocks) { stock ->
                    ItemRow(stock)
                }
            }
        }
    }
}

@Composable
fun ItemRow(
    stock: Stock,
    modifier: Modifier = Modifier
) {

    Divider(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .height(1.dp)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp)
    ) {
        Column {
            Text(
                text = stock.value.toStringMoney(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.paddingFromBaseline(24.dp)
            )
            Text(
                text = stock.status.toStatusString(),
                style = MaterialTheme.typography.body1,
                color = if (stock.status < 0.0) red else green,
                modifier = Modifier.paddingFromBaseline(16.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = stock.code,
                style = MaterialTheme.typography.h3,
                modifier = Modifier.paddingFromBaseline(24.dp)
            )
            Text(
                text = stock.name,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.paddingFromBaseline(16.dp)
            )
        }

        Image(
            painter = painterResource(id = stock.graph),
            contentDescription = stock.name,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
