package com.digitalsamurai.graphlib.ui.main

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import com.digitalsamurai.graphlib.database.room.nodes.NodePresentation
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode
import com.digitalsamurai.graphlib.database.room.nodes.node.entity.ChildNodes
import com.digitalsamurai.graphlib.database.room.nodes.position.NodePosition
import com.digitalsamurai.graphlib.database.room.nodes.properties.NodeViewProperty
import com.digitalsamurai.graphlib.ui.customscreen.bottom_navigator.ViewPosition
import com.digitalsamurai.graphlib.ui.main.vm.NodePresentationViewModel

@Composable
fun TreeLayout(modifier: Modifier, content: @Composable () -> Unit) {
    val lo = LocalConfiguration
    val screenWidthDp = lo.current.screenWidthDp
    val screenHeightDp = lo.current.screenHeightDp


    val horizontalScrollState = rememberScrollState(0)
    val verticalScrollState = rememberScrollState(0)

    Canvas(modifier = modifier) {
        drawRect(Color.Green)
    }
    Layout(
        modifier = modifier
            .verticalScroll(verticalScrollState, reverseScrolling = false)
            .horizontalScroll(horizontalScrollState, reverseScrolling = false),
        content = content
    ) { measurables, constraints ->
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val positionList = mutableListOf<ViewPosition>()
        val placeables = measurables.map { measurable ->
            // Measure each children
            positionList.add(measurable.parentData as ViewPosition)
            measurable.measure(constraints)

        }
        var maxWidth =
            positionList.maxBy { kotlin.math.abs(it.x) }.x + placeables.maxBy { it.width }.width
        var maxHeight =
            positionList.maxBy { kotlin.math.abs(it.y) }.y + placeables.maxBy { it.height }.height
        if (maxWidth < screenWidthDp) {
            maxWidth = screenWidthDp
        }
        if (maxHeight < screenHeightDp) {
            maxHeight = screenHeightDp
        }
        // Set the size of the layout as big as it can
//        layout(height = maxHeight, width = maxWidth) {
        layout(height = maxHeight, width = maxWidth) {

            // Place children in the parent layout

            for (i: Int in placeables.indices) {

                val placeable = placeables[i]
                val position = positionList[i]
                placeable.placeRelative(x = position.x, y = position.y)
                val a = placeable.height
                val b = placeable.width
                val c = placeable.measuredHeight
                val d = placeable.measuredWidth
                val e = 1

            }
            // Position item on the screen


        }


    }


}


@Preview
@Composable
fun previewTreeLayout() {
    val mockViewModel = object : NodePresentationViewModel {
        override fun clickEvent(nodePresentation: NodePresentation) {}
    }
    Box() {
        TreeLayout(
            Modifier
                .background(Color.White)
                .align(Alignment.Center)
        ) {
            NodePresentation(
                LibNode("", "title", 0, ChildNodes(), 0),
                NodePosition(0, 400, 400),
                NodeViewProperty(0, 100, 100)
            ).view(mockViewModel)
            NodePresentation(
                LibNode("", "title2", 0, ChildNodes(), 0),
                NodePosition(0, 300, 5000)
            ).view(mockViewModel)
        }
    }


}