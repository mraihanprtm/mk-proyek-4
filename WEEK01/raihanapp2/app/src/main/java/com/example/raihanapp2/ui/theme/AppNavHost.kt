package com.example.raihanapp2.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.raihanapp2.viewmodel.DataViewModel

@Composable
fun AppNavHost(viewModel: DataViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("form") {
            DataEntryScreen(navController = navController, viewModel = viewModel)
        }
        composable("list") {
            DataListScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = "edit/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            EditScreen(navController = navController, viewModel = viewModel, dataId = id)
        }
        composable("dashboard") {
            DashboardScreen(navController = navController, viewModel = viewModel)
        }

    }
}
