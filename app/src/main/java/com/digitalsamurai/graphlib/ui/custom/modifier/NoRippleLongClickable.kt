package com.digitalsamurai.graphlib.ui.custom.modifier

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.pointerInput

//TODO Need rework :(
fun Modifier.longClickable(clickTimeMillis : Long = 1000L, rippleEffect : Boolean? = false, onLongClick : ()->Unit) : Modifier {
    this.pointerInput(Unit){
        forEachGesture {
            awaitPointerEventScope {
                awaitFirstDown()
                val downTime = System.currentTimeMillis()
                do {
                    val event = awaitPointerEvent()

                } while (event.changes.any { it.pressed })
                if (System.currentTimeMillis()-downTime>=clickTimeMillis){
                    onLongClick.invoke()
                }
            }
        }
    }
    return this
}