package com.placek.maja.bmi

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(viewModel: BMIViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.BmiDescriptionScreen.route + "/{bmi}",
            arguments = listOf(
                navArgument("bmi"){
                    type = NavType.FloatType
                    defaultValue = 0.0
                    nullable = false
                }
            )
        ){entry ->
            BmiDescriptionScreen(navController = navController, bmi = entry.arguments?.getDouble("bmi"))
        }
        composable(
            route = Screen.HistoryScreen.route + "/{history}",
            arguments = listOf(
                navArgument("history"){
                    type = NavType.StringArrayType
                    defaultValue = 0.0
                    nullable = false
                }
            )
        ){entry ->
            BmiDescriptionScreen(navController = navController, bmi = entry.arguments?.getDouble("bmi"))
        }
        composable(
            route = Screen.AboutAuthorScreen.route,
        ){
            AboutAuthorScreen(navController = navController)
        }
    }
}

