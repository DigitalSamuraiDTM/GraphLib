package com.digitalsamurai.graphlib.ui.custom.modifier

import android.graphics.PointF
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import com.digitalsamurai.graphlib.ui.custom.tree_layout.TreeLayoutState

/**
 * Расширение позволяет кастомизировать управление слоем
 * Интересный факт, что два [pointerInput] работают вместе только в связке когда юзаются один за другим и ни в какой иначе
 * Т.е. нельзя отдельно вызвать два разных this.pointerInput и потом вернуть модификатор
 * Можно только последовательно связать [pointerInput] друг за другом и сразу вернуть модификатор
 *
 * ```
 * return pointerInput(Unit){
 *      //do something
 * }.pointerInput(Unit){
 *      //do something another
 * }
 *
 * ```
 * */
fun Modifier.treeLayoutPointerInput(
    state: TreeLayoutState,
    screenWidth: Float,
    screenHeight: Float,
    longClickEvent: LongClickEvent? = null,
    clickEvent: ((PointF) -> Unit)? = null,
): Modifier {

    var point = PointF()
    return pointerInput(Unit) {
        detectDragGestures { change, dragAmount ->
            change.consume()
            point = PointF(dragAmount.x, dragAmount.y)
            state.onDrag(IntOffset(dragAmount.x.toInt(), dragAmount.y.toInt()))
        }
    }.pointerInput(Unit) {
        forEachGesture {
            this.detectTapGestures(onLongPress = {
                longClickEvent?.onLongClick?.invoke()
            }, onTap = {
                //Offset который получаем = относителен начала ТЕКУЩЕГО элемента
                val stateX = state.offsetState.value.x
                val stateY = state.offsetState.value.y
                val coordinateX = it.x + stateX - screenWidth / 2
                val coordinateY = -(it.y + stateY - screenHeight / 2)
                clickEvent?.invoke(PointF(coordinateX, coordinateY))
            }
            )

        }
    }
}



