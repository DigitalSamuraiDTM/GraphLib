package com.digitalsamurai.graphlib.ui.createlib

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.digitalsamurai.graphlib.theme.MainTheme
import com.digitalsamurai.graphlib.ui.createlib.state.EnteredNameState
import com.digitalsamurai.graphlib.ui.createlib.vm.CreateLibViewModel
import com.digitalsamurai.graphlib.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CreateNewScreen(navController: NavController){

    MainTheme() {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            val viewModel: CreateLibViewModel = viewModel()
            val enteredNameState = viewModel.enteredName.value
            val enteredName = enteredNameState.enteredName
            val context = LocalContext.current

            LaunchedEffect(key1 = Unit, block = {
                viewModel.createLibFlow.collect{
                    if (it!=null){
                        navController.navigate(Screen.Main.route+"/${it}")
                    } else{
                        Toast.makeText(context,"Library is exist",Toast.LENGTH_SHORT).show()
                    }
                }
            })




            Column() {
                TopAppBar() {}

                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        when (enteredNameState) {
                            is EnteredNameState.NameExist -> Text(
                                text = "Library is exist",
                                style = TextStyle(color = Color.Red, fontSize = 18.sp)
                            )
                            is EnteredNameState.NameOk -> Text(
                                text = "Library name Ok",
                                style = TextStyle(color = Color.Green, fontSize = 18.sp)
                            )
                            is EnteredNameState.NameNothing -> Text(
                                text = "Enter library name",
                                style = TextStyle(fontSize = 18.sp)
                            )
                        }


                        OutlinedTextField(
                            modifier = Modifier,
                            value = enteredName,
                            textStyle = TextStyle(
                                color = Color.Red,
                                textAlign = TextAlign.Center
                            ),
                            label = { Text("Name", textAlign = TextAlign.Center) },
                            onValueChange = { viewModel.inputTextEvent(it) },
                        )
                        Button(
                            onClick = {
//                                navController.navigate(Screen.Main.route+"/${enteredName}")
                                viewModel.inputClickEvent()
                                      },
                            enabled = enteredName!="") {
                            Text(text = "Create")
                        }

                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun preview(){
    CreateNewScreen(navController = rememberNavController())
}