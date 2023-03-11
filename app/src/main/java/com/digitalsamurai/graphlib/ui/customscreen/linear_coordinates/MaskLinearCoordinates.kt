package com.digitalsamurai.graphlib.ui.customscreen.linear_coordinates

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.toSize

@Composable
fun MaskLinearCoordinates(){

    Canvas(modifier = Modifier.fillMaxSize().onGloballyPositioned { layoutCoordinates ->
        val w = layoutCoordinates.parentLayoutCoordinates?.size?.toSize()?.width
        val h = layoutCoordinates.parentLayoutCoordinates?.size?.toSize()?.height
        val a = 1
    }) {
        drawRect(Color.Green)
        drawLine(Color.Black, Offset(0f,0f), Offset(1000.toFloat(),5000.toFloat()))
    }
}