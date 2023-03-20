package com.digitalsamurai.graphlib.ui.main.bottom

import com.digitalsamurai.graphlib.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.digitalsamurai.graphlib.ui.SnackbarMessage
import com.digitalsamurai.graphlib.ui.main.MainScreenState
import com.digitalsamurai.graphlib.ui.main.vm.MainViewModelUI

/**
 * Используется только через [MainScreenState.NewNode]
 * Отображается в ситуации, когда пользователь создает новую (или первую) запись
 * */
@Composable
fun NewNodeStateBottomNavigation(viewModelUI: MainViewModelUI) {

    val state = viewModelUI.state.value as MainScreenState.NewNode


    //отображаем кнопку с навигацией на создание заголовка
    if (state.title == null) {


        Button(
            onClick = { viewModelUI.clickNavigationBottomButton() },
            modifier = Modifier.fillMaxSize().padding(10.dp, 0.dp, 10.dp, 10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Text(text = "Create root node")
        }


        return
    }
    //отображам снэк с сообщением выбрать родителя
    if (state.parentIndex == null) {
        SnackbarMessage(modifier = Modifier
            .fillMaxSize(), text = "Select parent of your node", painter = painterResource(
            id = R.drawable.ic_info_outline_black
        ))
        return
    }

    //отображаем снэк с сообщением выбрать место ноды
    if (state.position == null) {
        SnackbarMessage(modifier = Modifier
            .fillMaxSize(), text = "Place your node", painter = painterResource(
            id = R.drawable.ic_info_outline_black
        ))
        return
    }

}