package com.digitalsamurai.graphlib.ui.customscreen.tree_layout.linear_coordinates

import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.toSize

//todo кастомизация масок (цвет, размер, шаг сетки)
@Composable
fun MaskLinearCoordinates(){
    var defaultMaskColor = Color.Black

    val defaultMaskStepSize = 300

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

    var widthElement : Float? = -1f
    var heightElement : Float? = -1f
    Canvas(modifier = Modifier.fillMaxSize().onGloballyPositioned { layoutCoordinates ->
        //пытаемся получить размер элемента для понимания сетки отрисовки
        widthElement = layoutCoordinates.parentLayoutCoordinates?.size?.toSize()?.width
        heightElement = layoutCoordinates.parentLayoutCoordinates?.size?.toSize()?.height
    }) {
        drawIntoCanvas {canvas->

            if (widthElement!=null && heightElement!=null){
                var centerPoint = PointF(widthElement!!/2,heightElement!!/2f)


                //так как элемент прямоуголен, то можем одновременно отрисовывать сетку в обе стороны
                //рисуем сетку ординаты
                var stepsX = (centerPoint.x/defaultMaskStepSize).toInt()+1
                repeat(stepsX){step->
                    val xNextLine = centerPoint.x+(defaultMaskStepSize*(step+1))
                    val xPreviousLine = centerPoint.x-(defaultMaskStepSize*(step+1))
                    canvas.nativeCanvas.drawLine(xNextLine,0f,xNextLine,heightElement!!,linePaint)
                    canvas.nativeCanvas.drawLine(xPreviousLine,0f,xPreviousLine,heightElement!!,linePaint)
                }
                //рисуем сетку абсцисс
                val stepsY = (centerPoint.y/defaultMaskStepSize).toInt()+1
                repeat(stepsY){step->
                    val yNextLine = centerPoint.y+(defaultMaskStepSize*(step+1))
                    val yPreviousLine = centerPoint.y-(defaultMaskStepSize*(step+1))
                    canvas.nativeCanvas.drawLine(0f,yNextLine,widthElement!!,yNextLine,linePaint)
                    canvas.nativeCanvas.drawLine(0f,yPreviousLine,widthElement!!,yPreviousLine,linePaint)

                    //отрисовываем текст сетки


                }


                //повторем итерации и отрисовываем текст сетки чтобы он всегда был поверх сетки

                val textPadding = 10f
                repeat(stepsY){step->
                    val yNextLine = centerPoint.y+(defaultMaskStepSize*(step+1))
                    val yPreviousLine = centerPoint.y-(defaultMaskStepSize*(step+1))
                    //next текст это текст ниже оси абсцисс (но по логике андроида, имеющий значения координат больше)

                    val nextText = "-${defaultMaskStepSize*(step+1)}"
                    canvas.nativeCanvas.drawText(nextText,widthElement!!/2+textPadding,(yNextLine-linePaint.strokeWidth*2),textPaint)

                    val previousText = (defaultMaskStepSize*(step+1)).toString()
                    canvas.nativeCanvas.drawText(previousText,widthElement!!/2+textPadding,(yPreviousLine-linePaint.strokeWidth*2),textPaint)
                }
                repeat(stepsX){step->
                    val xNextLine = centerPoint.x+(defaultMaskStepSize*(step+1))
                    val xPreviousLine = centerPoint.x-(defaultMaskStepSize*(step+1))

                    val nextText = (defaultMaskStepSize*(step+1)).toString()
                    canvas.nativeCanvas.drawText(nextText,xNextLine+textPadding,heightElement!!/2-textPadding,textPaint)

                    val previousText = "-${defaultMaskStepSize*(step+1)}"
                    canvas.nativeCanvas.drawText(previousText,xPreviousLine+textPadding,heightElement!!/2-textPadding,textPaint)
                }


                //главные линии рисую отдельным цветом и после остальных линий, чтобы они были отрисованы поверх того, что уже есть

                //ордината
                canvas.nativeCanvas.drawLine(centerPoint.x,0f,centerPoint.x,heightElement!!,generalLinePaint)
                //абсцисса
                canvas.nativeCanvas.drawLine(0f,centerPoint.y,widthElement!!,centerPoint.y,generalLinePaint)




                canvas.nativeCanvas.drawText("0",centerPoint.x,centerPoint.y,textPaint)
            }
        }

    }
}