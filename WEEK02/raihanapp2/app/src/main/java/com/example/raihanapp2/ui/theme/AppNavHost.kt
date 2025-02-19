package com.example.raihanapp2.ui

import DataListScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.raihanapp2.ui.component.BottomNavItem
import com.example.raihanapp2.viewmodel.DataViewModel

@Composable
fun AppNavHost(navController: NavHostController, viewModel: DataViewModel) {
    NavHost(navController = navController, startDestination = BottomNavItem.Dashboard.route) {
        composable(BottomNavItem.Dashboard.route) {
            DashboardScreen(navController = navController, viewModel = viewModel)
        }
        composable(BottomNavItem.DataList.route) {
            DataListScreen(navController = navController, viewModel = viewModel)
        }
        composable(BottomNavItem.Profile.route) {
            Profile(navController = navController, viewModel = viewModel)
        }

        composable("form") {
            DataEntryScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = "edit/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("id") ?: 0
            EditScreen(navController = navController, viewModel = viewModel, dataId = id)
        }
    }
}
