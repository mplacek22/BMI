package com.placek.maja.bmi

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HistoryScreen(history: Set<String>?) {
    if (history != null) {
        if (history.isNotEmpty()) {
            Column {
                Text("BMI Calculation History:")
                for (entry in history) {
                    Text(entry)
                }
            }
        } else {
            Text("No history available.")
        }
    }
}