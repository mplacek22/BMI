package com.placek.maja.bmi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel  = BMIViewModel(this)
        setContent {
            MainScreen(navController = rememberNavController(), viewModel = viewModel)
//            BmiDescriptionScreen(navController = rememberNavController(), bmi = 20.0)
        }
    }
}