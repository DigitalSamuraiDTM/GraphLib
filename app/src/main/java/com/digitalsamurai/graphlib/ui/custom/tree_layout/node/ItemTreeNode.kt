package com.digitalsamurai.graphlib.ui.custom.tree_layout.node

import androidx.compose.runtime.Composable

typealias TreeNodeContent = @Composable (ItemTreeNode.TreeNodeData) -> Unit

/**
 * Base item data container for [TreeLayout]
 * */
data class ItemTreeNode(

    val data : TreeNodeData,
    val content : TreeNodeContent
){
    /**
     * Префы
     * */
    data class TreeNodePreferences(
        val x : Int,
        val y : Int,
        val width : Int,
        val height : Int
    )
    /**
     * [TreeNodeData] обеспечивает данные для отображения
     * */
    data class TreeNodeData(
        val title : String,
        val nodeIndex : Long,
        val preferences  : TreeNodePreferences
    )
}


//@Composable
//fun Node.toTreeNode(viewModel : NodeViewModel) : TreeNode{
//    val data = TreeNode.TreeNodeData(
//        title = this.nodeInfo.title,
//        nodeIndex = this.nodeViewProperty.nodeIndex,
//        preferences = TreeNode.TreeNodePreferences(
//            x = this.nodePosition.xPosition,
//            y = this.nodePosition.yPosition,
//            width = this.nodeViewProperty.width,
//            height = this.nodeViewProperty.height))
//
//    return TreeNode(
//        data = data, ::a)
//}
