package com.digitalsamurai.graphlib.ui.customscreen.tree_layout.modifier

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import com.digitalsamurai.graphlib.ui.customscreen.tree_layout.TreeLayoutState

//todo update custom painters
fun Modifier.drawLinearCoordinates(
    state: TreeLayoutState,
    screenWidth: Float,
    screenHeight: Float
): Modifier {
    val widthElement: Float = screenWidth
    val heightElement: Float = screenHeight
    val stepSize = 200
    val textPadding = 30


    var textPaint = Paint().also {
        it.color = android.graphics.Color.parseColor("#616060")
        it.textSize = 35f
    }
    val linePaint = Paint().also {
        it.color = android.graphics.Color.parseColor("#94908f")
        it.strokeWidth = 2f
    }
    val generalLinePaint = Paint().also {
        it.color = android.graphics.Color.parseColor("#0000FF")
        it.strokeWidth = 2f
    }


    return drawBehind {

        drawIntoCanvas { canvas ->


            //горизонтальная сетка

            val preStepY = stepSize - (heightElement/2 % stepSize)
            var countHorizontalLines = ((heightElement / stepSize)+2).toInt()
            val realOffsetY = state.offsetState.value.y % stepSize
            val countOffsetStepY = state.offsetState.value.y / stepSize

            repeat(countHorizontalLines) {
                val y = -preStepY+(stepSize * it) - realOffsetY.toFloat()
                val realYCoordinate = (-1*(it*stepSize+stepSize*countOffsetStepY-countHorizontalLines/2*stepSize))
                canvas.nativeCanvas.drawLine(
                    0f,
                    y,
                    widthElement,
                    y,
                    if (realYCoordinate==0) generalLinePaint else linePaint
                )
                val lineText = realYCoordinate.toString()
                val textRect = Rect().also {
                    textPaint.getTextBounds(lineText,0,lineText.length,it)
                }
                canvas.nativeCanvas.drawText(lineText,widthElement-textRect.width()-textPadding,y,textPaint)
            }

            //вертикальная сетка
            var preStepX =stepSize - (widthElement/2 % stepSize)
            val countVerticalLines = ((widthElement / stepSize)+2).toInt()
            val realOffsetX = state.offsetState.value.x % stepSize
            val countOffsetStepX = state.offsetState.value.x / stepSize
            repeat(countVerticalLines){
                val x = -preStepX+(stepSize*it)-realOffsetX
                val realXCoordinate = (it*stepSize+stepSize*countOffsetStepX-countVerticalLines/2*stepSize)
                canvas.nativeCanvas.drawLine(
                    x,
                    0f,
                    x,
                    heightElement,
                    if (realXCoordinate==0) generalLinePaint else linePaint

                )
                val lineText = realXCoordinate.toString()
                val textRect = Rect().also {
                    textPaint.getTextBounds(lineText,0,lineText.length,it)
                }
                canvas.nativeCanvas.drawText(lineText,x,0f+textRect.height()+textPadding,textPaint)
            }




        }
    }

}

