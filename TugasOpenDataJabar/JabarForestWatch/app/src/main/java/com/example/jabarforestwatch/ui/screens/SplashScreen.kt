package com.example.jabarforestwatch.ui.screens

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jabarforestwatch.data.OnboardingPrefs
import com.example.jabarforestwatch.ui.components.Screen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, context: Context) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            delay(3000)

            val isOnboardingShown = OnboardingPrefs.isOnboardingShown(context).first()
            val nextScreen = if (isOnboardingShown) Screen.Home.route else Screen.Onboarding.route

            navController.navigate(nextScreen) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    // UI Splash Screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Raihan",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "231511055",
                fontSize = 24.sp,
                color = Color.Gray
            )
        }
    }
}
