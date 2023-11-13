package com.placek.maja.bmi.viewmodel

data class ValueState(
    val suffix: String, // units
    val value: String = "", // A string representing the user's input value.
    val error: String? = null // error message if any
)
{
    fun toDouble() = value.toDoubleOrNull()
}
