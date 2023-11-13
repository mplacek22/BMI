package com.placek.maja.bmi.screens

sealed class Screen(val route: String){
    object MainScreen : Screen("main_screen")
    object HistoryScreen: Screen("history_screen")
    object BmiDescriptionScreen: Screen("bmi_description_screen")
    object AboutAuthorScreen: Screen("about_author_screen")

    fun withArgs( vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
}
