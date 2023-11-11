package com.placek.maja.bmi
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BMIViewModel(private val context: Context) : ViewModel(){
    var bmi by mutableStateOf(0.0)
        private set
    var result by mutableStateOf("")
        private set
    var selectedUnitMode by mutableStateOf(UnitMode.Metric)
        private set
    var heightState by mutableStateOf(ValueState("Height", "m"))
        private set
    var weightState by mutableStateOf(ValueState("Weight", "kg"))
        private set

    private var calculator: ICalculateBMI = BMICalculatorMetric()

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("BMIHistory", Context.MODE_PRIVATE)

    fun updateHeight(it: String) {
        heightState = heightState.copy(value = it, error = null)
    }

    fun updateWeight(it: String) {
        weightState = weightState.copy(value = it, error = null)
    }

    fun updateMode(it: UnitMode) {
        selectedUnitMode = it
        when (selectedUnitMode) {
            UnitMode.Imperial -> {
                heightState = heightState.copy(suffix = "in")
                weightState = weightState.copy(suffix = "lb")
                calculator = BMICalculatorImperial()
            }
            UnitMode.Metric -> {
                heightState = heightState.copy(suffix = "cm")
                weightState = weightState.copy(suffix = "kg")
                calculator = BMICalculatorMetric()
            }
        }
        clear()
    }

    fun clear() {
        heightState = heightState.copy(value = "", error = null)
        weightState = weightState.copy(value = "", error = null)
        bmi = 0.0
        result = ""
    }

    fun calculate() {
        val height = heightState.toDouble()
        val weight = weightState.toDouble()
        if (height == null)
            heightState = heightState.copy(error = "Invalid height value")
        else if (weight == null)
            weightState = weightState.copy(error = "Invalid weight value")
        else {
            bmi = calculator.calculate(height, weight)
            result = when {
                bmi < 18.5 -> context.getString(R.string.underweight)
                bmi in 18.5..24.9 -> context.getString(R.string.healthy_weight)
                bmi in 25.0..29.9 -> context.getString(R.string.overweight)
                bmi >= 30.0 -> context.getString(R.string.obesity)
                else -> error("Invalid params")
            }
            saveBMIHistory()
        }
    }

    fun canCalculate(): Boolean {
        return heightState.value != null && weightState.value != null
    }

    fun saveBMIHistory() {
        val history = sharedPreferences.getStringSet("history", setOf())?.toMutableSet() ?: mutableSetOf()
        history.add("$bmi: $result")
        sharedPreferences.edit().putStringSet("history", history).apply()
    }

    fun getBMIHistory(): Set<String> {
        return sharedPreferences.getStringSet("history", setOf()) ?: setOf()
    }
}