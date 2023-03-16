package com.digitalsamurai.graphlib.ui.custom.modifier

import android.graphics.PointF
import android.os.Vibrator
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
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
    longClickEvent: LongClickEvent? = null
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
            })
        }
    }
}



