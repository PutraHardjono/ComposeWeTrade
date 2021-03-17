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

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.viewmodel.LoginState
import com.example.androiddevchallenge.viewmodel.LoginViewModel

@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("DEPRECATION")
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            // Set the text/icon in StatusBar to white.
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

            // Set StatusBarBackgroundColor to transparent
            statusBarColor = Color.TRANSPARENT
        }

        setContent {
            val isDarkTheme by remember { mutableStateOf(false) }

            MyTheme(isDarkTheme) {
                MyApp(
                    isDarkTheme = isDarkTheme,
                    onSetStatusBar = { isBackgroundDark ->
                        if (isBackgroundDark) setStatusBarTextIconToWhite()
                        else setStatusBarTextIconToDark()
                    }
                )
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun setStatusBarTextIconToDark() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    @Suppress("DEPRECATION")
    private fun setStatusBarTextIconToWhite() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}

// Start building your app here!
@ExperimentalMaterialApi
@Composable
fun MyApp(
    viewModel: LoginViewModel = viewModel(),
    isDarkTheme: Boolean,
    onSetStatusBar: (isBackgroundDark: Boolean) -> Unit
) {

    when (viewModel.loginState) {
        LoginState.WELCOME -> WelcomeScreen(
            onLoginScreen = { viewModel.setLogin(LoginState.LOGIN) }
        )
        LoginState.LOGIN -> LoginScreen(
            isDarkTheme = isDarkTheme,
            onLogin = { viewModel.setLogin(LoginState.VALIDATED) }
        )
        LoginState.VALIDATED -> Home(
            isDarkTheme = isDarkTheme,
            onSetStatusBar = onSetStatusBar
        )
    }
}
