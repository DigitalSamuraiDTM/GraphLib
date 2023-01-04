package com.digitalsamurai.graphlib.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.view.View
import android.view.ViewGroup
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



    fun setOrigin(origin: Origin) {
        currentOrigin = origin
        if (this.isInLayout) {
            requestLayout()
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
            Origin.ORIGIN_COORDINATES -> {
                xOrigin = 0
                yOrigin = 0
            }
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
            drawConnectionRecyrcively(it,treeView.rootNode)
        }
        canvas?.drawCircle(xOrigin.toFloat(),yOrigin.toFloat(),10f, Paint().also{
            it.color = Color.BLACK
        })
        super.dispatchDraw(canvas)
    }


    fun drawConnectionRecyrcively(canvas : Canvas, node: TreeNode<ViewNode>){
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
        CENTER_VIEW, ORIGIN_COORDINATES
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
