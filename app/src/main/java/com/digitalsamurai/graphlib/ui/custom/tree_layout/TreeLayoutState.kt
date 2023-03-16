package com.digitalsamurai.graphlib.ui.custom.tree_layout

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset

@Composable
fun rememberTreeLayoutState(offset : IntOffset = IntOffset(0,0)) : TreeLayoutState{
    return remember { TreeLayoutState(offset) }
}

/**
 * [TreeLayoutState] реализовывает управление матрицей дерева. Позволяет крутить ее как удобно
 * */
@Stable
class TreeLayoutState(private val offset: IntOffset) {

    private val _offsetState = mutableStateOf(IntOffset(offset.x,offset.y))
    val offsetState : State<IntOffset>
    get() = _offsetState

    fun onDrag(offset : IntOffset){

        val x = (_offsetState.value.x - offset.x)
        val y = (_offsetState.value.y - offset.y)
        _offsetState.value = IntOffset(x,y)
    }

    fun getBoundaries(constraints : Constraints) : ViewBoundaries{

        val maxWidth = constraints.maxWidth
        val maxHeight = constraints.maxHeight
        val boundaries =  ViewBoundaries(
            fromX = offsetState.value.x - maxWidth,
            toX = constraints.maxWidth + offsetState.value.x + maxWidth,
            fromY = offsetState.value.y - maxHeight,
            toY = constraints.maxHeight + offsetState.value.y + maxHeight
        )
        return boundaries
    }


}