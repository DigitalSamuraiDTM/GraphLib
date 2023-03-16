package com.digitalsamurai.graphlib.ui.custom.modifier

import android.util.Log
import android.view.MotionEvent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import kotlinx.coroutines.*


@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.longClickable(
    coroutineScope: CoroutineScope,
    clickTimeMillis: Long = 1000L,
    rippleEffect: Boolean? = false,
    onLongClick: () -> Unit
): Modifier {

    this.pointerInteropFilter {
        val result =  when (it.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("GRAPH", "DOWN")
                launchTimer(coroutineScope,clickTimeMillis,onLongClick)
                true
            }
            MotionEvent.ACTION_UP -> {
                Log.d("GRAPH", "UP")
                coroutineScope.cancel()
                true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("GRAPH", "MOVE")
                coroutineScope.cancel()
                true
            }
            else -> {
                false
            }
        }
        return@pointerInteropFilter result
    }
    return this
}
private fun launchTimer(scope: CoroutineScope, eventTimeMillis : Long, event : () -> Unit){

    scope.launch {
        val current = System.currentTimeMillis()
        while (System.currentTimeMillis()-current<eventTimeMillis){
            delay(50)
        }
        event.invoke()

    }
}