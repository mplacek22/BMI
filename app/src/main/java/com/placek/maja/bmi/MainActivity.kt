package com.placek.maja.bmi

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.placek.maja.bmi.screens.Navigation
import com.placek.maja.bmi.viewmodel.BMIViewModel
import com.placek.maja.bmi.viewmodel.BMIViewModelFactory

class MainActivity : ComponentActivity() {
    private val sharedPreferencesKey = "BMIHistory"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = createViewModel()
            Navigation(viewModel = viewModel)
        }
    }

    private fun createViewModel(): BMIViewModel {
        val sharedPreferences = applicationContext.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        val viewModelFactory = BMIViewModelFactory(sharedPreferences)
        return ViewModelProvider(this, viewModelFactory)[BMIViewModel::class.java]
    }
}