package com.digitalsamurai.graphlib.ui.main

import android.graphics.PointF
import android.util.Log
import com.digitalsamurai.graphlib.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.digitalsamurai.graphlib.theme.GraphlibTheme
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorState
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorUi
import com.digitalsamurai.graphlib.ui.custom.modifier.LongClickEvent
import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode
import com.digitalsamurai.graphlib.ui.main.vm.MainViewModel
import com.digitalsamurai.graphlib.ui.main.vm.MainViewModelUI

@Composable
fun MainScreen(navController: NavController) {

    val viewModel: MainViewModel = viewModel()


    viewModel.library.value.let {
        Content(navController = navController, viewModel)
    }
}

@Composable
private fun Content(navController: NavController, viewModelUI: MainViewModelUI) {

    val fullScreenUpdateTransition = updateTransition(
        targetState = viewModelUI.isFullScreen,
        label = "LazyTreeFullScreen"
    )

    val haptic = LocalHapticFeedback.current

    val nodesList = viewModelUI.state.value.nodeList

    val focusedElement = if (viewModelUI.state.value is MainScreenState.Main) {
        (viewModelUI.state.value as MainScreenState.Main).focusedElement
    } else {
        null
    }



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
                    Text(text = viewModelUI.library.value)
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

                longClickEvent = LongClickEvent(500L) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModelUI.updateFullScreenState()
                }, clickEvent = {
                    viewModelUI.treeClick(it)
                }
            )
            {
                this.items(nodesList) {
                    NodeView(data = it, viewModel = viewModelUI)
                }
            }

            //Bottom элементы, которые отображаются в зависимости от состояний
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(60.dp)

            ) {
                viewModelUI.state.value.View(viewModelUI = viewModelUI)
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


    override fun treeClick(coordinate: PointF) {
        TODO("Not yet implemented")
    }

    override val library: State<String>
        get() = mutableStateOf("Obama")

    override fun clickEvent(nodeIndex: Long) {
        TODO("Not yet implemented")
    }

    private val _nodes = mutableStateListOf<ItemTreeNode.TreeNodeData>()


    private val _i = mutableStateOf(false)
    override val isFullScreen: State<Boolean>
        get() = _i

    private val _state = mutableStateOf<MainScreenState>(MainScreenState.Main(emptyList()))
    override val state: State<MainScreenState>
        get() = _state

    override fun updateFullScreenState(isFullScreen: Boolean?) {
        isFullScreen?.let {
            _i.value = it
            return
        }
        _i.value = !_i.value

    }

    override fun clickNavigationBottomButton() {
        TODO("Not yet implemented")
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