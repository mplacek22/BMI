package com.placek.maja.bmi.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.placek.maja.bmi.R

@Composable
fun getBMIColor(bmi: Double): Color {
    return when {
        bmi == 0.0 -> Color.Transparent
        bmi < 18.5 -> Color(LocalContext.current.resources.getColor(R.color.light_red, LocalContext.current.theme)) //underweight
        bmi < 24.9 -> Color(LocalContext.current.resources.getColor(R.color.light_green, LocalContext.current.theme)) // Normal weight
        bmi < 29.9 -> Color(LocalContext.current.resources.getColor(R.color.light_yellow, LocalContext.current.theme)) // Overweight
        else -> Color(LocalContext.current.resources.getColor(R.color.light_red, LocalContext.current.theme)) // Obese
    }
}