package com.digitalsamurai.graphlib.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
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
import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode
import com.digitalsamurai.graphlib.ui.main.vm.MainViewModel
import com.digitalsamurai.graphlib.ui.main.vm.MainViewModelUI
import com.digitalsamurai.graphlib.ui.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun MainScreen(navController: NavController, libName: String?) {

    val viewModel: MainViewModel = viewModel()

    viewModel.initData(libName)

    libName?.let {
        Content(navController = navController, viewModel)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Content(navController: NavController, viewModelUI: MainViewModelUI) {

    val fullScreenUpdateTransition = updateTransition(
        targetState = viewModelUI.isFullScreen,
        label = "LazyTreeFullScreen"
    )

    val haptic = LocalHapticFeedback.current

    val nodesList = viewModelUI.nodes

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
                }
            )
            {
                this.items(nodesList) {
                    NodeView(data = it, viewModel = viewModelUI)
                }
            }

            //кнопка всегда, если нет нод, иначе навигатор по ситуации
            if (nodesList.isEmpty()) {
                Button(
                    onClick = {
                        navController.navigate(Screen.CreateNode.route)},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(10.dp, 0.dp, 10.dp, 10.dp)
                ) {
                    Text(text = "Create your first node", color = Color.Black)
                }
            } else {
                AnimatedVisibility(
                    visible = (!viewModelUI.isFullScreen.value && viewModelUI.focusedElement.value != null),
                    enter = slideInVertically(animationSpec = tween(250)) { it },
                    exit = slideOutVertically(animationSpec = tween(250)) { it },
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

    override fun clickEvent(nodeIndex: Long) {
        TODO("Not yet implemented")
    }

    private val _nodes = mutableStateListOf<ItemTreeNode.TreeNodeData>()
    override val nodes: List<ItemTreeNode.TreeNodeData>
        get() = _nodes
    private val _f = mutableStateOf(null)
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