package com.digitalsamurai.graphlib.ui.start

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.digitalsamurai.graphlib.ui.navigation.Screen
import com.digitalsamurai.graphlib.ui.start.event.StartClickEvents
import com.digitalsamurai.graphlib.ui.start.states.StartScreenState
import com.digitalsamurai.graphlib.ui.start.vm.StartScreenViewModel

@Composable
fun StartScreen(navController: NavController){
        //init viewModel
    val viewModel : StartScreenViewModel = viewModel()
    val state = viewModel.state.value

    val startBtnNavigationRoute =
        if (state.lastGraph==null){
        Screen.Libs.route
    } else{
        Screen.Main.route
    }



    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)){
        //*
        Text(text = "Hello in GraphLib",
            modifier = Modifier
                .background(color = Color.White)
                .padding(4.dp),
            fontSize = 30.sp,
            textAlign = TextAlign.Center)
        //*
        Spacer(modifier = Modifier.height(60.dp))
        //*
        Button(
            modifier = Modifier.background(Color.White),
            enabled = true,
            onClick = { navController.navigate(startBtnNavigationRoute) }
        ){
            val text = if (state.lastGraph==null){"Begin"} else{"In ${state.lastGraph}"}
            Text(text = text,
                fontWeight = FontWeight.Bold)
        }
        //*
        if (state.lastGraph!=null) {
            Button(onClick = { navController.navigate(Screen.Libs.route) } ) {
                Text(text = "Graph list")
            }
        }
    }

}




@Composable
@Preview
fun previewStartScreen(){
     StartScreen(rememberNavController())
}