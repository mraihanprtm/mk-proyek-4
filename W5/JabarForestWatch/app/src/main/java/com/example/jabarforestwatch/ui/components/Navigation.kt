package com.example.jabarforestwatch.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jabarforestwatch.data.OnboardingPrefs
import com.example.jabarforestwatch.ui.DetailScreen
import com.example.jabarforestwatch.ui.screens.BookmarkScreen
import com.example.jabarforestwatch.ui.screens.DataScreen
import com.example.jabarforestwatch.ui.screens.EditScreen
import com.example.jabarforestwatch.ui.screens.HomeScreen
import com.example.jabarforestwatch.ui.screens.OnboardingScreen
import com.example.jabarforestwatch.ui.screens.ProfileScreen
import com.example.jabarforestwatch.ui.screens.SplashScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Data : Screen("data")
    object Profile : Screen("profile")
    object Bookmark : Screen("bookmark")
    object Detail : Screen("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
    object Edit : Screen("edit/{id}") {
        fun createRoute(id: Int) = "edit/$id"
    }
}

@Composable
fun AppNavHost(navController: NavHostController, context: Context) {
    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        OnboardingPrefs.isOnboardingShown(context).collect { isShown ->
            startDestination = if (isShown) Screen.Home.route else Screen.Onboarding.route
        }
    }

    if (startDestination != null) {
        NavHost(navController, startDestination = Screen.Splash.route) {
            composable(Screen.Splash.route) { SplashScreen(navController, context) }
            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onFinish = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                        CoroutineScope(Dispatchers.IO).launch {
                            OnboardingPrefs.setOnboardingShown(context)
                        }
                    }
                )
            }
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Data.route) {
                DataScreen(onForestClick = { id ->
                    navController.navigate(Screen.Detail.createRoute(id))
                })
            }
            composable(Screen.Profile.route) { ProfileScreen() }
            composable(Screen.Bookmark.route) { BookmarkScreen(navController) }

            composable(Screen.Detail.route, arguments = listOf(navArgument("id") { type = NavType.IntType })) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: -1
                DetailScreen(id = id, navController = navController)
            }

            composable(Screen.Edit.route, arguments = listOf(navArgument("id") { type = NavType.IntType })) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: -1
                EditScreen(id = id, navController = navController)
            }
        }
    }
}