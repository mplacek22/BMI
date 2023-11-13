package com.placek.maja.bmi.viewmodel
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.placek.maja.bmi.R
import com.placek.maja.bmi.calculator.BMICalculatorImperial
import com.placek.maja.bmi.calculator.BMICalculatorMetric
import com.placek.maja.bmi.calculator.ICalculateBMI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BMIViewModel(private val appContext: Context) : ViewModel(){
    var bmi by mutableDoubleStateOf(0.0)
        private set
    var category by mutableStateOf("")
        private set
    var selectedUnitMode by mutableStateOf(UnitMode.Metric)
        private set
    var heightState by mutableStateOf(ValueState(appContext.getString(R.string.height), "cm"))
        private set
    var weightState by mutableStateOf(ValueState(appContext.getString(R.string.weight), "kg"))
        private set

    private var calculator: ICalculateBMI = BMICalculatorMetric()

    private val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("BMIHistory", Context.MODE_PRIVATE)

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
        category = ""
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
            category = when {
                bmi < 18.5 -> appContext.getString(R.string.underweight)
                bmi < 24.9 -> appContext.getString(R.string.healthy_weight)
                bmi < 29.9 -> appContext.getString(R.string.overweight)
                else -> appContext.getString(R.string.obesity)
            }
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

        val historyEntry = "$currentDate, $formattedBMI, $weight, $height, $category"
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