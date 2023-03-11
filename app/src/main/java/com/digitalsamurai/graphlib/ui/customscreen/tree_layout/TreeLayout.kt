package com.digitalsamurai.graphlib.ui.main

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitalsamurai.graphlib.database.room.nodes.NodePresentation
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode
import com.digitalsamurai.graphlib.database.room.nodes.node.entity.ChildNodes
import com.digitalsamurai.graphlib.database.room.nodes.position.NodePosition
import com.digitalsamurai.graphlib.database.room.nodes.properties.NodeViewProperty
import com.digitalsamurai.graphlib.extensions.pxToDp
import com.digitalsamurai.graphlib.extensions.toPx
import com.digitalsamurai.graphlib.ui.customscreen.bottom_navigator.ViewPosition
import com.digitalsamurai.graphlib.ui.customscreen.linear_coordinates.MaskLinearCoordinates
import com.digitalsamurai.graphlib.ui.main.vm.NodePresentationViewModel

@Composable
fun TreeLayout(modifier: Modifier, content: @Composable () -> Unit) {
    val lo = LocalConfiguration
    // Получаем конфигу экрана в DP как число -> кастуем в dp -> кастуем в пиксели -> берем Int
    // Это необходимо потому что размеры элемента определяются по максимальной координате х и у,
    // которые в свою очередь считаются по пиксельно
    val screenWidthDp = lo.current.screenWidthDp.dp.toPx().toInt()
    val screenHeightDp = lo.current.screenHeightDp.dp.toPx().toInt()


    val horizontalScrollState = rememberScrollState(0)
    val verticalScrollState = rememberScrollState(0)

    val elementsPadding = 30



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
            val parent = try {
                measurable.parentData as ViewPosition
            } catch (e : java.lang.NullPointerException){
                //не добавлена ViewPosition дата
                ViewPosition(0,0)
            }
            parent?.let {
                positionList.add(parent)
            }
            measurable.measure(constraints)

        }
        //размеры элемента по максимальным координатам
        var maxWidth =
            positionList.maxBy { kotlin.math.abs(it.x) }.x + placeables.maxBy { it.width }.width + elementsPadding
        var maxHeight =
            positionList.maxBy { kotlin.math.abs(it.y) }.y + placeables.maxBy { it.height }.height + elementsPadding

        // Если элементов на экране недостаточно для того чтобы размер элемента был больше размера экрана,
        // то ставим текущий размер как размер экрана
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
    Box(modifier = Modifier.background(Color.White)) {
        TreeLayout(
            Modifier
                .align(Alignment.Center)
        ) {
            MaskLinearCoordinates()
            NodePresentation(
                LibNode("", "title", 0, ChildNodes(), 0),
                NodePosition(0, 0, 0),
                NodeViewProperty(0, 100, 100)
            ).view(mockViewModel)
            NodePresentation(
                LibNode("", "title2", 0, ChildNodes(), 0),
                NodePosition(0, 300, 5000)
            ).view(mockViewModel)
//            NodePresentation(
//                LibNode("", "title3", 0, ChildNodes(), 0),
//                NodePosition(0, 3000, 500)
//            ).view(mockViewModel)
        }
    }


}