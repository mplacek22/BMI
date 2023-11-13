package com.placek.maja.bmi.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.placek.maja.bmi.R

@Composable
fun getBMICategory(bmi: Double): String {
    return when {
        bmi == 0.0 -> ""
        bmi < 18.5 -> LocalContext.current.resources.getString(R.string.underweight)
        bmi < 24.9 -> LocalContext.current.resources.getString(R.string.healthy_weight)
        bmi < 29.9 -> LocalContext.current.resources.getString(R.string.overweight)
        else -> LocalContext.current.resources.getString(R.string.obesity)
    }
}