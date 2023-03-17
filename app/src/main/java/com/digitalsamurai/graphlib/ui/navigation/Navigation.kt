package com.digitalsamurai.graphlib.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.digitalsamurai.graphlib.ui.createlib.CreateNewScreen
import com.digitalsamurai.graphlib.ui.createnode.CreateNodeScreen
import com.digitalsamurai.graphlib.ui.libs.LibsScreen
import com.digitalsamurai.graphlib.ui.main.MainScreen
import com.digitalsamurai.graphlib.ui.start.StartScreen

@Composable
fun Navigation(lastGraph: String?) {

    val start = if (lastGraph == null) {
        Screen.Start.route
    } else {
        Screen.Main.route
    }

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = start) {
        composable(route = Screen.Start.route) {
            StartScreen(navController = navController)
        }
        composable(route = Screen.Libs.route) {
            LibsScreen(navController = navController)
        }
        composable(route = Screen.CreateNew.route) {
            CreateNewScreen(navController)
        }
        composable(route = Screen.Main.route){
            MainScreen(navController = navController)
        }

        composable(
            route = Screen.CreateNode.route + "/{lib_name}",
            arguments = listOf(navArgument("lib_name"){
                this.type = NavType.StringType
                this.nullable = false
            })
        ) {entry->
            CreateNodeScreen(navController, libraryName = entry.arguments?.getString("lib_name")!!)
        }
//        composable(route = Screen.CreateNode.route){
//            CreateNodeScreen(navController = navController)
//        }

    }
}