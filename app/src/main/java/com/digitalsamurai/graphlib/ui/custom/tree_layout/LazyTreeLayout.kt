package com.digitalsamurai.graphlib.ui.main

import android.graphics.PointF
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.digitalsamurai.graphlib.extensions.toPx
import com.digitalsamurai.graphlib.ui.custom.modifier.LongClickEvent
import com.digitalsamurai.graphlib.ui.custom.modifier.drawLinearCoordinates
import com.digitalsamurai.graphlib.ui.custom.modifier.treeLayoutPointerInput
import com.digitalsamurai.graphlib.ui.custom.tree_layout.TreeLayoutScope
import com.digitalsamurai.graphlib.ui.custom.tree_layout.TreeLayoutState
import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode
import com.digitalsamurai.graphlib.ui.custom.tree_layout.rememberTreeLayoutItemProvider
import com.digitalsamurai.graphlib.ui.custom.tree_layout.rememberTreeLayoutState
import com.digitalsamurai.graphlib.ui.main.vm.NodeViewModel

@Composable
fun LazyTreeLayout(
    modifier: Modifier,
    state: TreeLayoutState = rememberTreeLayoutState(),
    longClickEvent: LongClickEvent? = null,
    clickEvent: ((PointF) -> Unit)? = null,
    content: TreeLayoutScope.() -> Unit
) {


    val screenWidth = LocalConfiguration.current.screenWidthDp.dp.toPx()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp.toPx()
    //запомним провайдер, который поставляем нам наши элементы
    val provider = rememberTreeLayoutItemProvider(content)

    Log.d("TREE", state.offsetState.value.x.toString() + ":" + state.offsetState.value.y.toString())


    LazyLayout(
        modifier = modifier
            .clipToBounds()
            .treeLayoutPointerInput(
                state = state,
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                longClickEvent = longClickEvent,
                clickEvent = clickEvent
            )
            .drawLinearCoordinates(state, screenWidth, screenHeight),
        itemProvider = provider
    ) { constraints ->

        //получаем границы части слоя, на котором находится экран на основе constraint
        val boundaries = state.getBoundaries(constraints)


        //получаем список элементов для отображения через ItemProvider
        val indexes = provider.getItemInRange(boundaries)


        //мера каждого элемента для отображения
        val indexesWithPlaceable = indexes.associateWith {
            measure(
                it, Constraints(
                    minWidth = provider.getItem(it)?.data?.preferences?.width ?: 100,
                    maxWidth = provider.getItem(it)?.data?.preferences?.width ?: 100,
                    minHeight = provider.getItem(it)?.data?.preferences?.height ?: 100,
                    maxHeight = provider.getItem(it)?.data?.preferences?.height ?: 100
                )
            )
        }

        layout(width = constraints.maxWidth, height = constraints.maxHeight) {
            indexesWithPlaceable.forEach { (index, placeable) ->
                val item = provider.getItem(index)
                item?.let {
                    placeItem(
                        screenWidth = screenWidth.toInt(),
                        screenHeight = screenHeight.toInt(),
                        state = state,
                        listItem = it,
                        placeables = placeable
                    )
                }
            }
        }

    }

}

private fun Placeable.PlacementScope.placeItem(
    screenWidth: Int,
    screenHeight: Int,
    state: TreeLayoutState,
    listItem: ItemTreeNode,
    placeables: List<Placeable>
) {
    val xPosition =
        listItem.data.preferences.x + screenWidth / 2 - listItem.data.preferences.width / 2 - state.offsetState.value.x
    val yPosition =
        -listItem.data.preferences.y + screenHeight / 2 - listItem.data.preferences.height / 2 - state.offsetState.value.y

    placeables.forEach { placeable ->
        placeable.placeRelative(
            xPosition.toInt(),
            yPosition.toInt()
        )
    }
}


@Preview
@Composable
fun previewTreeLayout() {
    val mockViewModel = object : NodeViewModel {
        override fun clickEvent(nodeIndex: Long) {
            //NOTHING
        }
    }
    val list = listOf(
        ItemTreeNode.TreeNodeData("title", 0, ItemTreeNode.TreeNodePreferences(0, 0, 200, 200)),
        ItemTreeNode.TreeNodeData(
            "title2 title2 title2 title2 title2 title2 title2 title2 title2",
            1,
            ItemTreeNode.TreeNodePreferences(400, 600, 400, 200)
        ),
        ItemTreeNode.TreeNodeData(
            "title3",
            1,
            ItemTreeNode.TreeNodePreferences(1300, 1300, 100, 100)
        ),
        ItemTreeNode.TreeNodeData(
            "title4",
            1,
            ItemTreeNode.TreeNodePreferences(-300, -200, 100, 100)
        )
    )
    val layoutState = rememberTreeLayoutState()
    Box(modifier = Modifier.background(Color.White)) {
        LazyTreeLayout(
            modifier = Modifier.align(Alignment.Center),
            state = layoutState
        ) {
            items(data = list) {
                NodeView(data = it, viewModel = mockViewModel)
            }

        }
    }


}