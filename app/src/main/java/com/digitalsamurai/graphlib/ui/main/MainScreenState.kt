package com.digitalsamurai.graphlib.ui.main

import android.graphics.Point
import android.graphics.PointF
import androidx.compose.runtime.Composable
import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode
import com.digitalsamurai.graphlib.ui.main.bottom.NewNodeStateBottomNavigation
import com.digitalsamurai.graphlib.ui.main.vm.MainViewModelUI

sealed class MainScreenState(open val nodeList : List<ItemTreeNode.TreeNodeData>) {

    @Composable
    abstract fun View(viewModelUI: MainViewModelUI)

    class Main(
        override val nodeList: List<ItemTreeNode.TreeNodeData>,
        val focusedElement: ItemTreeNode.TreeNodeData? = null
    ) : MainScreenState(nodeList){
        @Composable
        override fun View(viewModelUI: MainViewModelUI)  = MainStateBottomNavigation(viewModelUI = viewModelUI)
    }

    /**
     * Создание новой ноды. Работать по принципу цепочки обязанностей, последовательно добавляя данные будет обновляться часть экрана
     * */
    class NewNode(
        override val nodeList: List<ItemTreeNode.TreeNodeData>,
        val title: String?,
        val parentIndex : Long?,
        var position : Point?
    ) : MainScreenState(nodeList){
        @Composable
        override fun View(viewModelUI: MainViewModelUI) = NewNodeStateBottomNavigation(viewModelUI = viewModelUI)
    }


}