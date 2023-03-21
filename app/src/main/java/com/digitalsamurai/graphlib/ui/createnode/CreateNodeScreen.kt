package com.digitalsamurai.graphlib.ui.createnode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.digitalsamurai.graphlib.R
import com.digitalsamurai.graphlib.theme.GraphlibTheme
import com.digitalsamurai.graphlib.ui.createnode.vm.CreateNodeViewModel
import com.digitalsamurai.graphlib.ui.createnode.vm.CreateNodeViewModelUI

@Composable
fun CreateNodeScreen(navController: NavController) {

    val viewModel: CreateNodeViewModel = viewModel()

    Content(navController = navController, viewModel = viewModel, libraryName = viewModel.libName.value)
}

@Composable
private fun Content(
    navController: NavController,
    viewModel: CreateNodeViewModelUI,
    libraryName: String
) {

    GraphlibTheme {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            TopAppBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .height(60.dp),

                ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_check_white),
                        contentDescription = "Check",
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.13f)
                            .align(Alignment.CenterEnd)
                            .padding(end = 10.dp)
                            .clickable {
                                if (viewModel.title.value.isEmpty()) {
                                    //show toast
                                } else {
                                    viewModel.applyNodeNameMediator()
                                    navController.popBackStack()
                                }
                            }
                    )

                }
            }
            TextField(
                value = viewModel.title.value, onValueChange = { value ->
                    viewModel.insertNodeTitle(value)
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
                    fontWeight = FontWeight.SemiBold
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent
                ),
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
        override fun applyNodeNameMediator() {
            TODO("Not yet implemented")
        }

        override val libName: State<String>
            get() = TODO("Not yet implemented")
        override val title: State<String>
            get() = TODO("Not yet implemented")

        override fun insertNodeTitle(title: String) {
            TODO("Not yet implemented")
        }
    }
    Content(navController = controller, viewModel, "Тестик")
}