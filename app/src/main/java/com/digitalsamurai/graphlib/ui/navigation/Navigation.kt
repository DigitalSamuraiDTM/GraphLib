package com.digitalsamurai.graphlib.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.digitalsamurai.graphlib.ui.createlib.CreateNewScreen
import com.digitalsamurai.graphlib.ui.libs.LibsScreen
import com.digitalsamurai.graphlib.ui.main.MainScreen
import com.digitalsamurai.graphlib.ui.start.StartScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Start.route){
        composable(route=Screen.Start.route){
            StartScreen(navController = navController)
        }
        composable(route = Screen.CreateNew.route){
            CreateNewScreen(navController)
        }
        composable(route = (Screen.Main.route+"/{lib_name}"), arguments = listOf(
            navArgument("lib_name"){
                this.type = NavType.StringType
                this.nullable = false
            }
        )){entry->
            MainScreen(navController = navController, libName =  entry.arguments?.getString("lib_name"))
        }
        composable(route = Screen.Libs.route){
            LibsScreen(navController)
        }

    }
}