package com.digitalsamurai.graphlib.ui.libs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.digitalsamurai.graphlib.database.room.libs.Lib
import com.digitalsamurai.graphlib.theme.MainTheme
import com.digitalsamurai.graphlib.ui.libs.item.LibraryItem
import java.time.LocalDateTime

@Composable
fun LibsScreen(navController: NavController){
    MainTheme() {
//        val viewModel = GraphLibApp.appComponent.getLibsViewModel()
//        val flow = viewModel.libsFlow.collectAsState(emptyList())
//
//        val update = remember {
//            val data = mutableListOf<Lib>()
//
//            derivedStateOf {
//
//                data
//            }
//        }

        Surface(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            Column() {
                TopAppBar(modifier = Modifier
                    .weight(1f)
                    .shadow(10.dp)) {
                }
                LazyColumn(modifier = Modifier.weight(15f),
                    contentPadding = PaddingValues(5.dp)){
                    item{
                        LibraryItem(lib = Lib("test", LocalDateTime.now()))
                    }
                    
                }
            }
        }
    }
}

@Composable
@Preview
fun previewLibsScreen(){
    LibsScreen(rememberNavController())
}