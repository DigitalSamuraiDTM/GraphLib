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
import com.digitalsamurai.graphlib.theme.MainTheme
import com.digitalsamurai.graphlib.ui.libs.item.LibraryItem
import com.digitalsamurai.graphlib.ui.navigation.Screen
import com.digitalsamurai.graphlib.ui.start.vm.StartScreenViewModel
import kotlinx.coroutines.flow.collect
import java.time.LocalDateTime

@Composable
fun LibsScreen(navController: NavController){
        val viewModel : LibsScreenViewModel = viewModel()

        val libsState = viewModel.libsState.collectAsState().value
//        val libsState = emptyList<Lib>()


        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Box() {
                //*

                //*
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center) {
                    TopAppBar(
                        modifier = Modifier
                            .shadow(20.dp, RectangleShape,
                                ambientColor = Color.White,
                                spotColor = Color.Black)

                    ) {}

                    if (libsState.isEmpty()){
                        //Show empty
                        Box(modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = "Empty! But it is not a problem",
                                textAlign = TextAlign.Center)

                        }
                    } else {
                        //show data
                        LazyColumn(
                            modifier = Modifier
                                .weight(weight = 1f, fill = false),
                            contentPadding = PaddingValues(5.dp)
                        ) {
                            items(libsState) {
                                LibraryItem(lib = it, modifier = Modifier.clickable {
                                    navController.navigate(Screen.Main.route+"/"+it.libName)
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
    LibsScreen(rememberNavController())
}