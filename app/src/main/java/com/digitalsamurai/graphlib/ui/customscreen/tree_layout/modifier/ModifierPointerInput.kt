package com.digitalsamurai.graphlib.ui.customscreen.tree_layout.modifier

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import com.digitalsamurai.graphlib.ui.customscreen.tree_layout.TreeLayoutState

/**
 * Расширение позволяет кастомизировать управление слоем
 * */
fun Modifier.treeLayoutPointerInput(state : TreeLayoutState) : Modifier {
    return pointerInput(Unit){
        detectDragGestures { change, dragAmount ->
            change.consume()
            state.onDrag(IntOffset(dragAmount.x.toInt(), dragAmount.y.toInt()))
        }
    }
}