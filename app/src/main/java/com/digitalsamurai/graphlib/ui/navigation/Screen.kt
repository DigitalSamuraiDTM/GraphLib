package com.digitalsamurai.graphlib.ui.navigation

sealed class Screen<T : Unit>(val route : String) {

    open fun  navigationWithArgs(args : T) : String{
        return this.route
    }

    object Start : Screen<Unit>("start_screen")

    object Libs : Screen<Unit>("libs_screen")

    object Main : Screen<Unit>("main_screen")

    object CreateNew : Screen<Unit>("create_new")

    object CreateNode : Screen<Unit>("create_node")
}