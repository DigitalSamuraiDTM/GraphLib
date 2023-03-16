package com.digitalsamurai.graphlib.ui.createnode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.digitalsamurai.graphlib.theme.GraphlibTheme
import com.digitalsamurai.graphlib.ui.createnode.vm.CreateNodeViewModelUI

@Composable
fun CreateNodeScreen(navController: NavController, viewModel: CreateNodeViewModelUI, libraryName : String) {

    Content(navController = navController, viewModel = viewModel, libraryName = libraryName)
}

@Composable
private fun Content(navController: NavController, viewModel: CreateNodeViewModelUI, libraryName: String) {

    GraphlibTheme {
        var name = remember { mutableStateOf("") }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            TopAppBar(
                Modifier
                    .align(Alignment.TopCenter)
                    .height(60.dp)
            ) {}
            TextField(
                value = name.value, onValueChange = { value ->
                    name.value = value
                },
                placeholder = {
                    Text("Example of your title in graph ${libraryName}", fontSize = 35.sp)
                },
                
                modifier = Modifier
                    .padding(top = 65.dp)
                    .background(Color.White)
                    .fillMaxSize()
                    .align(Alignment.Center),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.SemiBold),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent),
                shape = RoundedCornerShape(0.dp)
            )


        }
    }

}

@Composable
@Preview
private fun PreviewNodeScreen() {

    val controller = rememberNavController()
    val viewModel = object : CreateNodeViewModelUI {

    }
    Content(navController = controller, viewModel = viewModel, "Тестик")
}