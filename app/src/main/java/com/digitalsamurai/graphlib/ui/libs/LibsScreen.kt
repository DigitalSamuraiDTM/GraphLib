package com.digitalsamurai.graphlib.ui.libs

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.digitalsamurai.graphlib.database.room.libs.Lib
import com.digitalsamurai.graphlib.ui.libs.item.LibraryItem
import com.digitalsamurai.graphlib.ui.libs.vm.LibsScreenViewModelUI
import com.digitalsamurai.graphlib.ui.navigation.Screen
import kotlinx.coroutines.flow.Flow

@Composable
fun LibsScreen(navController: NavController){
        val viewModel : LibsScreenViewModel = viewModel()

        val libsState = viewModel.libsState.collectAsState(emptyList()).value

        LibsScreenContent(navController = navController, viewModel = viewModel)



}

@Composable
private fun LibsScreenContent(navController: NavController,
                    viewModel : LibsScreenViewModelUI
){
    val libsState = viewModel.libsState.collectAsState(emptyList()).value
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Box(Modifier.fillMaxSize()) {
            //*

            //*
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center
            ) {
                TopAppBar(
                    modifier = Modifier
                        .shadow(
                            5.dp, RectangleShape,
                            ambientColor = Color.White,
                            spotColor = Color.Black
                        )

                ) {}

                if (libsState.isEmpty()) {
                    //Show empty
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Empty! But it is not a problem",
                            textAlign = TextAlign.Center
                        )

                    }
                } else {
                    //show data
                    LazyColumn(
                        modifier = Modifier,
                        contentPadding = PaddingValues(5.dp)
                    ) {
                        items(libsState) {
                            LibraryItem(
                                lib = it,
                                modifier = Modifier.clickable(enabled = true) {
                                    viewModel.selectLib(it.libName)
                                    navController.navigate(Screen.Main.route){
                                        this.popUpTo(0)
                                    }
                                })
                        }
                    }
                }
            }
            //*
            FloatingActionButton(
                onClick = { navController.navigate(Screen.CreateNew.route) },
                modifier = Modifier
                    .padding(0.dp, 0.dp, 20.dp, 20.dp)
                    .width(60.dp)
                    .height(60.dp)
                    .align(Alignment.BottomEnd)

            ) {}

        }
    }
}

@Composable
@Preview
fun previewLibsScreen(){
    LibsScreenContent(rememberNavController(),object : LibsScreenViewModelUI{
        override val libsState: Flow<List<Lib>>
            get() = TODO("Not yet implemented")

        override fun selectLib(libName: String) {
            TODO("Not yet implemented")
        }
    })
}