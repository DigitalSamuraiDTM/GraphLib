package com.digitalsamurai.graphlib.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.digitalsamurai.graphlib.ui.main.vm.MainViewModel
import com.digitalsamurai.graphlib.ui.main.vm.MainViewModelUI

@Composable
fun MainScreen(navController: NavController, libName : String?){

    val viewModel : MainViewModel = viewModel()

    viewModel.initData(libName)

    libName?.let {
        Content(navController = navController, viewModel)
    }
}

@Composable
private fun Content(navController: NavController,viewModelUI: MainViewModelUI){

    Box(modifier = Modifier
        .background(color = Color.White)
        .fillMaxSize()){
        Text(text = "Hello in ${viewModelUI.library.value}")

    }
}

@Preview
@Composable
fun preview(){
    //Viewmodel need Mock

    val mockViewModel = object : MainViewModelUI{
        override val library: State<String>
            get() = mutableStateOf("Obama")
    }

    Content(navController = rememberNavController(),mockViewModel)
}
