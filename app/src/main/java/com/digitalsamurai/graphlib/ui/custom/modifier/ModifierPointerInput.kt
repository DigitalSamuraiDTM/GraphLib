package com.digitalsamurai.graphlib.ui.custom.modifier

import android.graphics.PointF
import android.util.Log
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import com.digitalsamurai.graphlib.ui.custom.tree_layout.TreeLayoutState

/**
 * Расширение позволяет кастомизировать управление слоем
 * Интересный факт, что два [pointerInput] работают вместе только в такой связке и ни в какой иначе
 * Т.е. нельзя отдельно вызвать два разных this.pointerInput и потом вернуть модификатор
 * Можно только последовательно связать [pointerInput] друг за другом и сразу вернуть модификатор
 * */
fun Modifier.treeLayoutPointerInput(state : TreeLayoutState, longClickEvent: LongClickEvent? = null) : Modifier {

    var point = PointF()
     return pointerInput(Unit){
        detectDragGestures { change, dragAmount ->
            change.consume()
            point = PointF(dragAmount.x,dragAmount.y)
            state.onDrag(IntOffset(dragAmount.x.toInt(), dragAmount.y.toInt()))
        }
    }.pointerInput(Unit){
        forEachGesture {
            awaitPointerEventScope {
                awaitFirstDown()
                val downTime = System.currentTimeMillis()
                //сохранем место последнего клика, для того чтобы понимать различие между свайпом и долгим кликом
                val lastPointF = point
                Log.d("GRAPH", "SAVED POINT: ${lastPointF}")
                do {
                    val event = awaitPointerEvent()
                } while (event.changes.any { it.pressed })

                //пользователь двигал палец по экрану, значит он свайпал, а не производил долгое нажатие
                if (point.x!=lastPointF.x && point.y != lastPointF.y){
                    return@awaitPointerEventScope
                }

                if (System.currentTimeMillis() - downTime >= (longClickEvent?.clickTimeMillis ?: 1000L) ){
                    Log.d("GRAPH", "SAVED POINT ON UNPRESSED: ${lastPointF}")
                    longClickEvent?.onLongClick?.invoke()
                }
            }
        }
    }


}