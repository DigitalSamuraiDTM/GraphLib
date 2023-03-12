package com.digitalsamurai.graphlib.ui.main

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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

//    var defaultBoxSize = 500000
    var defaultBoxSize = 1000000


    //1374900 For pixel 5
    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()

    defaultBoxSize = defaultBoxSize.dp.toPx().toInt()

    val elementsPadding = 30



    Layout(
        modifier = modifier
            ,
        content = content
    ) { measurables, constraints ->




        // Don't constrain child views further, measure them with given constraints
        // List of measured children

        val positionList = mutableListOf<ViewPosition?>()
        val placeables = measurables.map { measurable ->
            // Measure each children
            val parent = try {
                measurable.parentData as ViewPosition
            } catch (e : java.lang.NullPointerException){
                //не добавлена ViewPosition дата
                null
            }

            positionList.add(parent)

            measurable.measure(constraints)

        }

        //Дефолтный размер коробки
        layout(height = defaultBoxSize, width = defaultBoxSize) {

            // Place children in the parent layout

            for (i: Int in placeables.indices) {

                val placeable = placeables[i]
                val position = positionList[i]

                val widthPlaceable = placeable.height
                val heightPlaceable = placeable.width

                if (position != null){
                    placeable.placeRelative(
                        x = defaultBoxSize/2+position.x-widthPlaceable/2,
                        y = defaultBoxSize/2-position.y-heightPlaceable/2
                    )
                } else{
                    placeable.placeRelative(
                        x = 0,
                        y = 0)
                }



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
            NodePresentation(
                LibNode("", "title3", 0, ChildNodes(), 0),
                NodePosition(0, 3000, 500)
            ).view(mockViewModel)
        }
    }


}