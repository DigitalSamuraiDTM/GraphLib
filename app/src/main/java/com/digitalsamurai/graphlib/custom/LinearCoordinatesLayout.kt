package com.digitalsamurai.graphlib.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsAnimation.Bounds
import com.digitalsamurai.graphlib.math.tree.TreeNode
import java.lang.NullPointerException
import kotlin.math.abs

@SuppressLint("ViewConstructor")
class LinearCoordinatesLayout(context : Context, private var treeView: TreeView) : ViewGroup(
    context
){

    private var countView = 0

    var xOrigin: Int = 0
    var yOrigin: Int = 0

    private var maxXElement = 0
    private var maxYElement = 0

    private var currentOrigin = Origin.CENTER_VIEW

    //key - view Id
    //value - view position
    private var mPositions: HashMap<Int, CoordinatesPosition> = HashMap()


    //красота для координатной сетки
    private var colorMatrix = Color.parseColor("#000000")
    private var strokeWithMatrix = 1f
    private var textSizeMatrix = 30f
    private var paintMatrix = Paint().also {
        it.color = colorMatrix
        it.strokeWidth = strokeWithMatrix
        it.textSize = textSizeMatrix
    }

    //сопутствующие дополнительные данные для координатной сетки
    private var stepLineMatrix : Float = 250f

    //отображение координатной сетки
    var isMatrixEnabled : Boolean = false
    set(value) {
        field = value
        requestLayout()
    }



    fun setOrigin(origin: Origin) {
        currentOrigin = origin
        if (this.isInLayout) {
            invalidate()
        }

    }




    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var newWidth : Int = maxXElement.toInt()*2
        var newHeight : Int = maxYElement.toInt()*2
        val currentWidth = MeasureSpec.getSize(widthMeasureSpec)
        if (newWidth<currentWidth){
            newWidth = currentWidth
        }
        val currentHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (newHeight<currentHeight){
            newHeight = currentHeight
        }
        val newWidthMeasure = MeasureSpec.makeMeasureSpec(newWidth,MeasureSpec.EXACTLY)
        val newHeightMeasure = MeasureSpec.makeMeasureSpec(newHeight,MeasureSpec.EXACTLY)
        super.onMeasure(newWidthMeasure, newHeightMeasure)
    }

    override fun addView(child: View?) {

        super.addView(child)
    }

    //функция отвечающая за расположение элементов внутри этого слоя
    override fun onLayout(p0: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //todo check
        when (currentOrigin) {
            Origin.CENTER_VIEW -> {
                xOrigin = width / 2
                yOrigin = height / 2
            }
//            Origin.ORIGIN_COORDINATES -> {
//                xOrigin = 0
//                yOrigin = 0
//            }
        }

        repeat(childCount) {
            val view = getChildAt(it)
            val viewPosition = mPositions.get(view.hashCode()) ?: CoordinatesPosition(100, 100,view)
            val elementCenterWidth = view.layoutParams.width / 2
            val elementCenterHeight = view.layoutParams.height / 2

            val elementXPos = xOrigin+viewPosition.xPos
            val elementYPos = yOrigin-viewPosition.yPos

            val left = elementXPos-elementCenterWidth
            val right = left+elementCenterWidth*2
            val top = elementYPos-elementCenterHeight
            val bottom = elementYPos+elementCenterHeight
            view.layout(
                left.toInt(),
                top.toInt(),
                right.toInt(),
                bottom.toInt()
            )

        }
    }

    override fun dispatchDraw(canvas: Canvas?) {


        canvas?.let {
            drawMatrix(it)

            drawConnectionRecyrcively(it,treeView.rootNode)
        }
        canvas?.drawCircle(xOrigin.toFloat(),yOrigin.toFloat(),10f, Paint().also{
            it.color = Color.BLACK
        })
        super.dispatchDraw(canvas)
    }

    private fun drawMatrix(canvas: Canvas){
        if (isMatrixEnabled){
            when(currentOrigin){
                Origin.CENTER_VIEW -> {

                    var abscissaStep = xOrigin.toFloat()
                    var bound = Rect()
                    paintMatrix.getTextBounds("0",0,1,bound)

                    //Vertical lines
                    canvas.drawLine(abscissaStep,0f,abscissaStep,this.height.toFloat(),paintMatrix)
                    canvas.drawText("0",abscissaStep+10,bound.height().toFloat()+10,paintMatrix)

                    while (abscissaStep+stepLineMatrix<this.width){
                        abscissaStep+= stepLineMatrix
                        canvas.drawLine(abscissaStep,0f,abscissaStep,this.height.toFloat(),paintMatrix)
                        canvas.drawText((abscissaStep-xOrigin).toString(),abscissaStep+10,bound.height().toFloat()+10,paintMatrix)
                    }

                    abscissaStep = xOrigin.toFloat()
                    while (abscissaStep-stepLineMatrix>0){
                        abscissaStep-= stepLineMatrix
                        canvas.drawLine(abscissaStep,0f,abscissaStep,this.height.toFloat(),paintMatrix)
                        canvas.drawText((abscissaStep-xOrigin).toString(),abscissaStep+10,bound.height().toFloat()+10,paintMatrix)

                    }


                    //Horizontal lines
                    var ordinateStep = yOrigin.toFloat()
                    canvas.drawLine(0f,ordinateStep,this.width.toFloat(),ordinateStep,paintMatrix)
                    canvas.drawText("0",10f,ordinateStep-10,paintMatrix)
                    while (ordinateStep+stepLineMatrix<this.height){
                        ordinateStep+= stepLineMatrix
                        canvas.drawLine(0f,ordinateStep,this.width.toFloat(),ordinateStep,paintMatrix)
                        canvas.drawText(((ordinateStep-yOrigin)*-1).toString(),10f,ordinateStep-10,paintMatrix)
                    }
                    ordinateStep = yOrigin.toFloat()
                    while (ordinateStep-stepLineMatrix>0){
                        ordinateStep-= stepLineMatrix
                        canvas.drawLine(0f,ordinateStep,this.width.toFloat(),ordinateStep,paintMatrix)
                        canvas.drawText(((ordinateStep-yOrigin)*-1).toString(),10f,ordinateStep-10,paintMatrix)
                    }

                }
            }
        }
    }

    /**
     * рекурсивно отображаем линии взаимосвязей между узлами дерева
     * @param node - нода, дети которых будут отрисовываться
     * @param canvas - канва для отрисовки
     * @return Ничего не возвращает
     * */
    private fun drawConnectionRecyrcively(canvas : Canvas, node: TreeNode<ViewNode>){
        node.childNodes.forEach {

            val absolutePointRoot = getAbsoluteCenter(node.data.viewPosition)
            val absolutePointChild = getAbsoluteCenter(it.data.viewPosition)
            canvas.drawLine(
                absolutePointRoot.x.toFloat(),
                absolutePointRoot.y.toFloat(),
                absolutePointChild.x.toFloat(),
                absolutePointChild.y.toFloat(),
                Paint().also {
                it.color = Color.RED
                it.strokeWidth = 2f
            })
            drawConnectionRecyrcively(canvas,it)
        }
    }

    private fun getAbsoluteCenter(point : Point) : Point{
        return Point(xOrigin+point.x,yOrigin-point.y)
    }

    //user for center view
    inner class CoordinatesPosition {
         var xPos : Int
         var yPos : Int

         private var view : View? = null
        constructor(view : View){
            this.xPos = 0
            this.yPos = 0
            this.view = view
            connect(view)
            }

        constructor(xPos : Int, yPos : Int, view: View){
            this.xPos = xPos
            this.yPos = yPos
            this.view = view
            connect(view)
        }
         public constructor(xPos : Int, yPos : Int){
            this.xPos = xPos
            this.yPos = yPos

        }
        public constructor(point : Point, view : View){
            this.xPos = point.x
            this.yPos = point.y
            this.view = view
            connect(view)
        }

        fun addViewInLayout(){
            if (view!=null){
                this@LinearCoordinatesLayout.addView(this.view)
                requestLayout()
//                if (this@LinearCoordinatesLayout.isInLayout){
//                    this@LinearCoordinatesLayout.requestLayout()
//                }
            } else{
                throw NullPointerException("VIEW IN COORDINATES NOT FOUND")
            }
        }


        fun connect(view : View){
            if (abs(xPos)+view.layoutParams.width >maxXElement){
                maxXElement = abs(xPos)+view.layoutParams.width
            }
            if (abs(yPos)+view.layoutParams.height>maxYElement){
                maxYElement = abs(yPos)+view.layoutParams.height
            }
            //todo change to hash function
            if (mPositions.get(view.hashCode())==null){
                mPositions.put(view.hashCode(),this)
            } else{
                //update
                mPositions.put(view.hashCode(),this)
            }
        }
    }
    enum class Origin{
        CENTER_VIEW,
//        ORIGIN_COORDINATES
    }



//    inner class Zoomer : ScaleGestureDetector.OnScaleGestureListener{
//
//        override fun onScale(p0: ScaleGestureDetector?): Boolean {
//            Log.d("TAG", "${p0?.scaleFactor}")
//            p0?.let {
//                if (p0.scaleFactor>1f){
//                    this@LinearCoordinatesLayout.scaleX = this@LinearCoordinatesLayout.scaleX+0.01f
//                } else if (p0.scaleFactor<1f){
//                    this@LinearCoordinatesLayout.scaleX = this@LinearCoordinatesLayout.scaleX-0.01f
//                }
//
//            }
//            return true
//        }
//
//        override fun onScaleBegin(p0: ScaleGestureDetector?): Boolean {
//            return true
//        }
//
//        override fun onScaleEnd(p0: ScaleGestureDetector?) {
//
//        }

//    }
}
