package com.placek.maja.bmi.viewmodel
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.placek.maja.bmi.calculator.BMICalculatorImperial
import com.placek.maja.bmi.calculator.BMICalculatorMetric
import com.placek.maja.bmi.calculator.ICalculateBMI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BMIViewModel(
    private val sharedPreferences: SharedPreferences
) : ViewModel(){
    var bmi by mutableDoubleStateOf(0.0)
        private set
    var selectedUnitMode by mutableStateOf(UnitMode.Metric)
        private set
    var heightState by mutableStateOf(ValueState("cm"))
        private set
    var weightState by mutableStateOf(ValueState("kg"))
        private set

    private var calculator: ICalculateBMI = BMICalculatorMetric()

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
            saveBMIHistory()
        }
    }

    fun canCalculate(): Boolean {
        return heightState.value != "" && weightState.value != ""
    }

    private fun saveBMIHistory() {
        val history = sharedPreferences.getString("history", null)?.let {
            Gson().fromJson<List<String>>(it, object : TypeToken<List<String>>() {}.type)
        }?.toMutableList() ?: mutableListOf()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        val formattedBMI = String.format(Locale.US, "%.1f", bmi)
        val weight = "${weightState.value} ${weightState.suffix}"
        val height = "${heightState.value} ${heightState.suffix}"

        val historyEntry = "$currentDate, $formattedBMI, $weight, $height"
        history.add(0, historyEntry)

        while (history.size > 10) {
            history.removeAt(history.size - 1)
        }

        val historyJson = Gson().toJson(history)
        sharedPreferences.edit().putString("history", historyJson).apply()
    }

    fun clearBMIHistory() {
        sharedPreferences.edit().remove("history").apply()
    }

    fun getBMIHistory(): List<String> {
        val historyJson = sharedPreferences.getString("history", null)
        return if (historyJson != null) {
            Gson().fromJson(historyJson, object : TypeToken<List<String>>() {}.type)
        } else {
            emptyList()
        }
    }
}