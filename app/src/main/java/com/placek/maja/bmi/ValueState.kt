package com.placek.maja.bmi

data class ValueState(
    val label: String, // lable
    val suffix: String, // units
    val value: String = "", // A string representing the user's input value.
    val error: String? = null // error message if any
)
{
    fun toDouble() = value.toDoubleOrNull()
}
