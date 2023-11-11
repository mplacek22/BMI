package com.placek.maja.bmi

class BMICalculatorImperial: ICalculateBMI {
    override fun calculate(heightInIn: Double, weightInLb: Double): Double {
        return 703 * weightInLb / heightInIn / heightInIn
    }
}