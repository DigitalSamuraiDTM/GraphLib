package com.digitalsamurai.graphlib.ui.navigation

sealed class Screen(val route : String) {
    object Start : Screen("start_screen")
    object Libs : Screen("libs_screen")
    object Main : Screen("main_screen")
    object CreateNew : Screen("create_new")
}