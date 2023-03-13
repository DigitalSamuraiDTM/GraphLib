package com.digitalsamurai.graphlib.ui.main

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.digitalsamurai.graphlib.extensions.toPx
import com.digitalsamurai.graphlib.ui.customscreen.bottom_navigator.ViewPosition
import com.digitalsamurai.graphlib.ui.customscreen.tree_layout.*
import com.digitalsamurai.graphlib.ui.customscreen.tree_layout.node.ItemTreeNode
import com.digitalsamurai.graphlib.ui.main.vm.NodeViewModel

@Composable
fun LazyTreeLayout(modifier: Modifier,
                   state : TreeLayoutState = rememberTreeLayoutState(),
                   content: TreeLayoutScope.()->Unit) {

    //определяем дефолтный размер матрицы
    var defaultBoxSize = 500
    defaultBoxSize = defaultBoxSize.dp.toPx().toInt()


    //запомним провайдер, который поставляем нам наши элементы
    val provider = rememberTreeLayoutItemProvider(content)

    Log.d("TREE",state.offsetState.value.x.toString()+":"+state.offsetState.value.y.toString())

    LazyLayout(
        modifier = modifier
            .clipToBounds()
            .treeLayoutPointerInput(state),
        itemProvider = provider) { constraints ->

        //получаем границы части слоя, на котором находится экран на основе constraint
        val boundaries = state.getBoundaries(constraints)


        //получаем список элементов для отображения через ItemProvider
        val indexes = provider.getItemInRange(boundaries)


        //мера каждого элемента для отображения
        val indexesWithPlaceable = indexes.associateWith {
            measure(it, Constraints())
        }

        layout(width = constraints.maxWidth, height = constraints.maxHeight){
            indexesWithPlaceable.forEach { (index,placeable) ->
                val item = provider.getItem(index)
                item?.let { placeItem(state,it,placeable) }
            }
        }

    }

}

private fun Placeable.PlacementScope.placeItem(state: TreeLayoutState, listItem: ItemTreeNode, placeables: List<Placeable>) {
    val xPosition = listItem.data.preferences.x - state.offsetState.value.x
    val yPosition = listItem.data.preferences.y - state.offsetState.value.y

    placeables.forEach { placeable ->
        placeable.placeRelative(
            xPosition,
            yPosition
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
        ItemTreeNode.TreeNodeData("title",0,ItemTreeNode.TreeNodePreferences(0,0,200,200)),
        ItemTreeNode.TreeNodeData("title2",1,ItemTreeNode.TreeNodePreferences(300,600,200,200)),
        ItemTreeNode.TreeNodeData("title3",1,ItemTreeNode.TreeNodePreferences(1300,1300,200,200))
        )
    val layoutState = rememberTreeLayoutState()
    Box(modifier = Modifier.background(Color.White)) {
        LazyTreeLayout(
            modifier = Modifier.align(Alignment.Center),
            state = layoutState
        ) {
            items(data = list){
                NodeView(data = it, viewModel = mockViewModel)
            }

        }
    }


}