package com.digitalsamurai.graphlib.ui.main

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.digitalsamurai.graphlib.database.room.nodes.NodePresentation
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode
import com.digitalsamurai.graphlib.database.room.nodes.node.entity.ChildNodes
import com.digitalsamurai.graphlib.database.room.nodes.position.NodePosition
import com.digitalsamurai.graphlib.database.room.nodes.properties.NodeViewProperty
import com.digitalsamurai.graphlib.ui.main.vm.NodePresentationViewModel

@Composable
fun TreeLayout(modifier : Modifier,content : @Composable () -> Unit) {
    val lo = LocalConfiguration
    val width = lo.current.screenWidthDp
    val height = lo.current.screenHeightDp



    val horizontalScrollState = rememberScrollState(0)
    val verticalScrollState = rememberScrollState(0)

    Canvas(modifier = modifier){
        drawRect(Color.Green,)
    }
    Layout(modifier = modifier
        .verticalScroll(verticalScrollState, reverseScrolling = false)
        .horizontalScroll(horizontalScrollState, reverseScrolling = false),
            content = content){measurables, constraints ->
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
         val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)

        }


        // Set the size of the layout as big as it can
        layout(constraints.maxHeight,constraints.maxWidth) {


            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen

                placeable.placeRelative(x = 0, y = 0)
                val a = placeable.height
                val b = placeable.width
                val c = placeable.measuredHeight
                val d = placeable.measuredWidth
                val e = 1


            }
        }


    }



}



fun Modifier.position(x : Int, y : Int) = this then object : LayoutModifier{

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val a = measurable.measure(constraints)
        val w =a.width
        val h = a.height
        val mw = a.measuredWidth
        val mh = a.measuredHeight

        val horizontal = HorizontalAlignmentLine { a, b ->
            a+b
        }

        return object : MeasureResult{
            override val alignmentLines: Map<AlignmentLine, Int>
                get() = mapOf(horizontal to x)
            override val height: Int
                get() = h
            override val width: Int
                get() = w

            override fun placeChildren() {}
        }
    }


}



@Preview
@Composable
fun previewTreeLayout(){
    val mockViewModel = object : NodePresentationViewModel{
        override fun clickEvent(nodePresentation: NodePresentation) {}
    }
    TreeLayout(
        Modifier
            .background(Color.White)
            .graphicsLayer(scaleX = 1f, scaleY = 1f)) {
        NodePresentation(
            LibNode("", "title", 0, ChildNodes(), 0),
            NodePosition(0,400,400),
            NodeViewProperty(0,300,150)
        ).view(mockViewModel)
        NodePresentation(
            LibNode("", "title2", 0, ChildNodes(), 0),
            NodePosition(0, 300, 5000)
        ).view(mockViewModel)
    }


}