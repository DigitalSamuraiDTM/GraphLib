package com.digitalsamurai.graphlib.ui.main.bottom

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Text(text = "Create root node")
        }


        return
    }
    //отображам снэк с сообщением выбрать родителя
    if (state.parentIndex == null) {

        return
    }

    //отображаем снэк с сообщением выбрать место ноды
    if (state.position == null) {

        return
    }

}