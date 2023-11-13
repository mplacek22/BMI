package com.placek.maja.bmi.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.placek.maja.bmi.viewmodel.BMIViewModel

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
                    defaultValue = 0.0f
                    nullable = false
                }
            )
        ){entry ->
            BmiDescriptionScreen(navController = navController, bmi = entry.arguments?.getFloat("bmi")?.toDouble())
        }
        composable(
            route = Screen.HistoryScreen.route + "/{history}",
            arguments = listOf(
                navArgument("history"){
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                }
            )
        ){entry ->
            HistoryScreen(navController = navController, history = entry.arguments?.getString("history")?.split(";")?.toList() ?: emptyList())
        }
        composable(
            route = Screen.AboutAuthorScreen.route,
        ){
            AboutAuthorScreen(navController = navController)
        }
    }
}

