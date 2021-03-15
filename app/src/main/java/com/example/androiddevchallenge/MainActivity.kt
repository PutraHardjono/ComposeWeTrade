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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.viewmodel.LoginState
import com.example.androiddevchallenge.viewmodel.LoginViewModel

@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme by remember { mutableStateOf(true) }
            MyTheme(isDarkTheme) {
                MyApp(isDarkTheme = isDarkTheme)
            }
        }
    }
}

// Start building your app here!
@ExperimentalMaterialApi
@Composable
fun MyApp(viewModel: LoginViewModel = viewModel(), isDarkTheme: Boolean) {

    when (viewModel.loginState) {
        LoginState.WELCOME -> WelcomeScreen(
            onLoginScreen = { viewModel.setLogin(LoginState.LOGIN) }
        )
        LoginState.LOGIN -> LoginScreen(
            isDarkTheme = isDarkTheme,
            onLogin = { viewModel.setLogin(LoginState.VALIDATED) }
        )
        LoginState.VALIDATED -> Home()
    }
}

@ExperimentalMaterialApi
@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp(isDarkTheme = false)
    }
}

@ExperimentalMaterialApi
@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp(isDarkTheme = true)
    }
}
