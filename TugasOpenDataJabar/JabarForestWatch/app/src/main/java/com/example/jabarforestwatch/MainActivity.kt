package com.example.jabarforestwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.jabarforestwatch.ui.MainScreen
import com.example.jabarforestwatch.ui.theme.JabarForestWatchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JabarForestWatchTheme {
                MainScreen()
            }
        }
    }
}