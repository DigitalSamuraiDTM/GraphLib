package com.digitalsamurai.graphlib.ui.custom.bottom_navigator

import androidx.compose.animation.*
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
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorState
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorUi


@Composable
fun BottomNavigator(

    viewModel: BottomNavigatorViewModel,
    modifier: Modifier = Modifier
) {
        val value = viewModel.navigatorState.value

    //сравниваем состояния
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

                AnimatedVisibility(
                    visible = value.prevoius!=null,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    enter = slideInHorizontally(),
                    exit = slideOutHorizontally()
                ) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .clickable {
                            value.prevoius?.let {
                                viewModel.bottomNavigatorClicked(value.prevoius)
                            }
                        }) {
                        Text(text = value.prevoius?.title ?: "",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(
                                Alignment.Center))
                    }
                }
            AnimatedVisibility(
                visible = value.prevoius!=null,
                modifier = Modifier.widthIn(10.dp)) {
                Box(modifier = Modifier.clickable { viewModel.bottomNavigatorClicked(value.current) }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_double_arrow_right_black),
                        contentDescription = "next"
                    )
                }

            }
            //CURRENT
            Box(modifier = Modifier
                .fillMaxSize()
                .weight(1f)) {
                Text(text = value.current.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(
                        Alignment.Center))
            }
            AnimatedVisibility(
                visible = value.next.isNotEmpty(),
                modifier = Modifier.widthIn(10.dp)) {
                Box() {
                    Image(
                        painter = painterResource(id = R.drawable.ic_double_arrow_right_black),
                        contentDescription = "next"
                    )
                }
            }
            AnimatedVisibility(
                visible = value.next.isNotEmpty(),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                enter = slideInHorizontally(),
                exit = slideOutHorizontally()
            ) {
                val nextPreview = if (value.next.isNotEmpty()) value.next[0] else null

                Box(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clickable {
                        nextPreview?.let {
                            viewModel.bottomNavigatorClicked(nextPreview)
                        }
                    }){
                    Text(text = nextPreview?.title ?: "",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center))
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

        BottomNavigator(
            mockViewModel,
            Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp)
        )


    }
}

private val mockViewModel = object : BottomNavigatorViewModel {


    override fun bottomNavigatorClicked(element: BottomNavigatorUi) {
        if (element.nodeIndex==0L){
            _navigatorState.value = BottomNavigatorState(null,c0, listOf(c1))
        } else if (element.nodeIndex==1L){
            _navigatorState.value = BottomNavigatorState(c0,c1, listOf(c2,c3,c4))
        } else if (element.nodeIndex==2L){
            _navigatorState.value = BottomNavigatorState(c1,c2, listOf(c3))
        } else if (element.nodeIndex==3L){
            _navigatorState.value = BottomNavigatorState(c2,c3, listOf())
        }
    }

    val c0 = BottomNavigatorUi("c0",0)
    val c1 = BottomNavigatorUi("c1",1)
    val c2 = BottomNavigatorUi("c2",2)
    val c3 = BottomNavigatorUi("c3",3)
    val c4 = BottomNavigatorUi("c4",4)

    private var _navigatorState = mutableStateOf<BottomNavigatorState>(
        BottomNavigatorState(c0,c1,
            listOf(c2,c4)
        )
    )
    override val navigatorState: State<BottomNavigatorState>
    get() = _navigatorState
}
