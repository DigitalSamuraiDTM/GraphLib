package com.digitalsamurai.graphlib.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.BottomNavigator
import com.digitalsamurai.graphlib.ui.main.vm.MainViewModelUI

/**
 *  НЕ явная связь [MainScreenState.Main]
 *
 *  Можно реализовать явную связь, использую вместо [MainViewModelUI] интерфейс только с текущим состоянием, но это излишество
 *  Поэтому оставляю смарткаст на определенное состояние. Жертва, благодаря которой я получаю хорошую декомпозицию
 */

@Composable
fun MainStateBottomNavigation(viewModelUI: MainViewModelUI) {

    val state = viewModelUI.state.value as MainScreenState.Main
    val focusedElement = state.focusedElement

    if (state.nodeList.isNotEmpty()) {
        AnimatedVisibility(
            visible = (!viewModelUI.isFullScreen.value && focusedElement != null),
            enter = slideInVertically(animationSpec = tween(250)) { it },
            exit = slideOutVertically(animationSpec = tween(250)) { it },
        ) {
            BottomNavigator(
                viewModel = viewModelUI,
                modifier = Modifier
                    .shadow(10.dp)
                    .background(Color.White)
            )
        }
    }
    //Отображение только если есть хоть одна нода


}
