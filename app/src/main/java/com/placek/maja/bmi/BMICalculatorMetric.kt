package com.placek.maja.bmi

class BMICalculatorMetric : ICalculateBMI {
    override fun calculate(heightInCm: Double, weightInKg: Double): Double {
        return weightInKg / heightInCm / heightInCm * 10000
    }
}