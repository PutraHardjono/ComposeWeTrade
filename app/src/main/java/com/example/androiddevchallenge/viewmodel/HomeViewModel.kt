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
package com.example.androiddevchallenge.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.Chip
import com.example.androiddevchallenge.model.Stock

class HomeViewModel : ViewModel() {
    var stocks by mutableStateOf(
        listOf(
            Stock("ALK", "Alaska Air Group, Inc.", 7918.0, -0.5, R.drawable.ic_home_alk),
            Stock("BA", "Boeing Co.", 1293.0, 4.18, R.drawable.ic_home_ba),
            Stock("DAL", "Delta Airlines Inc.", 893.50, -0.54, R.drawable.ic_home_dal),
            Stock("EXPE", "Expedia Group Inc.", 12301.0, 2.51, R.drawable.ic_home_exp),
            Stock("EADSY", "Airbus SE", 12301.0, 1.38, R.drawable.ic_home_eadsy),
            Stock("JBLU", "Jetblue Airways Corp.", 8521.0, 1.56, R.drawable.ic_home_jblu),
            Stock("MAR", "Marriot International Inc.", 521.0, 2.75, R.drawable.ic_home_mar),
            Stock("CCL", "Carnival Corp.", 5481.0, 0.14, R.drawable.ic_home_ccl),
            Stock("RCL", "Royal Caribbean Cruises", 9184.0, 1.69, R.drawable.ic_home_rcl),
            Stock("TRVL", "Travelocity Inc.", 654.0, 3.23, R.drawable.ic_home_trvl),
        )
    )
        private set

    var chips by mutableStateOf(
        listOf(
            Chip("Week", Icons.Outlined.ExpandMore),
            Chip("ETFs"),
            Chip("Stocks"),
            Chip("Funds"),
            Chip("Other")
        )
    )
        private set

    var balance by mutableStateOf(73589.01)
    var status by mutableStateOf(412.35)
}
