package com.digitalsamurai.graphlib.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController, libName : String?){
    Box(modifier = Modifier
        .background(color = Color.White)
        .fillMaxSize()){
        Text(text = "Hello in ${libName}")
    }
}