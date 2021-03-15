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

import java.text.NumberFormat
import java.util.Locale
import kotlin.math.truncate

private var numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

fun Double.toStringMoney(): String {
    val result = this - truncate(this)
    numberFormat.maximumFractionDigits = if (result != 0.0) 2 else 0
    return numberFormat.format(this)
}

fun Double.toStatusString(): String {
    return when {
        this > 0 -> "+%.2f%%".format(this)
        this < 0 -> "%.2f%%".format(this)
        else -> "0%"
    }
}
