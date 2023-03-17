package com.digitalsamurai.graphlib.ui.main

import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode

sealed class MainScreenState {

    class Main(val focusedElement : ItemTreeNode.TreeNodeData? = null) : MainScreenState()

    class NewNode(val title : String?) : MainScreenState()


}