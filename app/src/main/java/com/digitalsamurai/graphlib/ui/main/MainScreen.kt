package com.digitalsamurai.graphlib.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.digitalsamurai.graphlib.theme.GraphlibTheme
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.BottomNavigator
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorState
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorUi
import com.digitalsamurai.graphlib.ui.custom.modifier.LongClickEvent
import com.digitalsamurai.graphlib.ui.main.vm.MainViewModel
import com.digitalsamurai.graphlib.ui.main.vm.MainViewModelUI

@Composable
fun MainScreen(navController: NavController, libName: String?) {

    val viewModel: MainViewModel = viewModel()

    viewModel.initData(libName)

    libName?.let {
        Content(navController = navController, viewModel)
    }
}

@Composable
private fun Content(navController: NavController, viewModelUI: MainViewModelUI) {

    val fullScreenUpdateTransition = updateTransition(
        targetState = viewModelUI.isFullScreen,
        label = "LazyTreeFullScreen"
    )

    GraphlibTheme() {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AnimatedVisibility(
                visible = !viewModelUI.isFullScreen.value,
                enter = slideInVertically(animationSpec = tween(250)),
                exit = slideOutVertically(animationSpec = tween(250)) { -it },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .height(60.dp)
            ) {

                TopAppBar() {
                    Text(text = "${viewModelUI.library.value}")
                }
            }


            val lazyTreePadding = fullScreenUpdateTransition.animateDp(
                label = "LazyTreeFullScreen"
            ) {
                when (it.value) {
                    true -> {
                        0.dp
                    }
                    false -> {
                        60.dp
                    }
                }
            }

            LazyTreeLayout(
                modifier = Modifier
                    .padding(0.dp, lazyTreePadding.value, 0.dp, 0.dp),
                longClickEvent = LongClickEvent(500) {
                    viewModelUI.updateFullScreenState()
                }
            )
            {
                this.items(emptyList()) {

                }
            }
            AnimatedVisibility(
                visible = (!viewModelUI.isFullScreen.value && viewModelUI.focusedElement.value != null),
                enter = slideInVertically(animationSpec = tween(250)){ it },
                exit =  slideOutVertically(animationSpec = tween(250)){ it },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {


                BottomNavigator(
                    viewModel = viewModelUI,
                    modifier = Modifier

                        .padding(10.dp, 0.dp, 10.dp, 10.dp)
                        .shadow(10.dp)
                        .background(Color.White)

                )
            }


        }
    }
}

@Preview
@Composable
fun preview() {
    //Viewmodel need Mock


    Content(navController = rememberNavController(), mockViewModel)
}

//test mock
private val mockViewModel = object : MainViewModelUI {
    override val library: State<String>
        get() = mutableStateOf("Obama")


    private val _f = mutableStateOf(0L)
    override val focusedElement: State<Long?>
        get() = _f
    private val _i = mutableStateOf(false)
    override val isFullScreen: State<Boolean>
        get() = _i

    override fun updateFullScreenState(isFullScreen: Boolean?) {
        isFullScreen?.let {
            _i.value = it
            return
        }
        _i.value = !_i.value

    }

    override fun bottomNavigatorClicked(element: BottomNavigatorUi) {
        TODO("Not yet implemented")
    }

    override val navigatorState: State<BottomNavigatorState>
        get() = mutableStateOf(
            BottomNavigatorState(
                null,
                BottomNavigatorUi("-1", 0L),
                emptyList()
            )
        )
}