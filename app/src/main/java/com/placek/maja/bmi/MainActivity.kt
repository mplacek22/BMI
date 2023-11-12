package com.placek.maja.bmi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel  = BMIViewModel(this)
        setContent {
            Navigation(viewModel = viewModel)
        }
    }
}