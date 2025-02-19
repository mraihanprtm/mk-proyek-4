package com.example.raihanapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.raihanapp2.ui.AppNavHost
import com.example.raihanapp2.ui.theme.HanyarunrunTheme
import com.example.raihanapp2.viewmodel.DataViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HanyarunrunTheme {
//                // Inisialisasi ViewModel
//                val dataViewModel: DataViewModel = viewModel()
//                // Menampilkan Navigation Host
//                AppNavHost(viewModel = dataViewModel)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color =MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}
