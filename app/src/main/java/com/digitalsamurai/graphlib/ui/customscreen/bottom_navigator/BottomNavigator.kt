package com.digitalsamurai.graphlib.ui.customscreen.bottom_navigator

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitalsamurai.graphlib.R
import com.digitalsamurai.graphlib.ui.customscreen.bottom_navigator.entity.BottomNavigatorState
import com.digitalsamurai.graphlib.ui.customscreen.bottom_navigator.entity.BottomNavigatorUi


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavigator(

    viewModel: BottomNavigatorViewModel,
    modifier: Modifier = Modifier
) {

    AnimatedContent(targetState = viewModel.navigatorState,
        transitionSpec = {
            slideInHorizontally(
                animationSpec = tween(300),
                initialOffsetX = { fullWidth: Int -> fullWidth }
            ) with
                    slideOutHorizontally(
                        animationSpec = tween(300),
                        targetOffsetX = { fullWidth -> -fullWidth }
                    )
        }) { state->

        val value = state.value
        Row(
            modifier = modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            when(value){
                BottomNavigatorState.NoElements -> {
                    Text(text = "No focused element")
                }
                is BottomNavigatorState.Root -> {
                    //CURRENT
                    Box(Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .clickable { viewModel.bottomNavigatorClicked(value.current.nodeIndex) }) {
                        Text(text = value.current.title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center))
                    }
                    //NEXT
                    if (value.child!=null){
                        Box(modifier = Modifier
                            .widthIn(10.dp)){
                            Image(painter = painterResource(id = R.drawable.ic_double_arrow_right_black),"Next element")
                        }
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .clickable { viewModel.bottomNavigatorClicked(value.child.nodeIndex) }){
                            Text(
                                text = value.child.title,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center))
                        }
                    }

                }
                is BottomNavigatorState.ChainElement -> {
                    //PREVIOUS
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .clickable { viewModel.bottomNavigatorClicked(value.parent.nodeIndex) }){
                        Text(
                            text = value.parent.title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center))
                    }
                    Box(modifier = Modifier
                        .widthIn(10.dp)){
                        Image(painter = painterResource(id = R.drawable.ic_double_arrow_right_black),"Next element")
                    }
                    //CURRENT
                    Box(Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .clickable { viewModel.bottomNavigatorClicked(value.current.nodeIndex) }) {
                        Text(text = value.current.title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center))
                    }

                    //CHILD
                    val child = value.childs[0]
                    Box(modifier = Modifier
                        .widthIn(10.dp)){
                        Image(painter = painterResource(id = R.drawable.ic_double_arrow_right_black),"Next element")
                    }
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .clickable { viewModel.bottomNavigatorClicked(child.nodeIndex) }){
                        Text(
                            text = child.title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center))
                    }

                }
                is BottomNavigatorState.LastElement -> {
                    //PREVIOUS
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .clickable { viewModel.bottomNavigatorClicked(value.parent.nodeIndex) }){
                        Text(
                            text = value.parent.title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center))
                    }
                        Box(modifier = Modifier
                            .widthIn(10.dp)){
                            Image(painter = painterResource(id = R.drawable.ic_double_arrow_right_black),"Next element")
                        }
                    //CURRENT
                    Box(Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .clickable { viewModel.bottomNavigatorClicked(value.current.nodeIndex) }) {
                        Text(text = value.current.title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center))
                    }

                }
            }


        }
    }

}


@Composable
@Preview
private fun PreviewBottomNavigator() {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {

        AnimatedVisibility(visible = true) {
            BottomNavigator(
                mockViewModel,
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(10.dp)
            )

        }
    }
}

private val mockViewModel = object : BottomNavigatorViewModel {


    override fun bottomNavigatorClicked(index: Long) {
        if (index==3L || index==4L){
            _navigatorState.value = last
        }
        if (index==10L){
            _navigatorState.value = chain
        }
        if (index==12L){
            _navigatorState.value = rootWithChild
        }
        if (index==1L){
            _navigatorState.value = chain
        }
    }

    val childsList = listOf(
        BottomNavigatorUi("child1",3),
        BottomNavigatorUi("child2",4))

    val rootWithChild = BottomNavigatorState.Root(
        BottomNavigatorUi("rootrootrootrootrootrootrootrootrootrootrootroot",0),
        BottomNavigatorUi("childchildchildchildchildchildchildchildchildchild",1))
    val rootNoChild = BottomNavigatorState.Root(
        BottomNavigatorUi("rootrootrootrootrootrootrootrootrootrootrootroot",0),null)
    val noElements = BottomNavigatorState.NoElements
    val chain = BottomNavigatorState.ChainElement(
        parent = BottomNavigatorUi("parent",12),
        current = BottomNavigatorUi("current",1),
        childs = childsList)
    val last = BottomNavigatorState.LastElement(
        parent = BottomNavigatorUi("parent",10),
        current = BottomNavigatorUi("current",11)
    )

    private var _navigatorState = mutableStateOf<BottomNavigatorState>(chain)
    override val navigatorState: State<BottomNavigatorState>
        get() = _navigatorState
}
