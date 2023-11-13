package com.placek.maja.bmi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.placek.maja.bmi.viewmodel.BMIViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel  = BMIViewModel(applicationContext)
        setContent {
            Navigation(viewModel = viewModel)
        }
    }
}