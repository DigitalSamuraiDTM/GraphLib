package com.digitalsamurai.graphlib.logic.mappers

import com.digitalsamurai.graphlib.database.room.nodes.Node
import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode

fun Node.toLazyTreeMutableList(): MutableList<ItemTreeNode.TreeNodeData> {
    val nodeData = mutableListOf(
        ItemTreeNode.TreeNodeData(
            this.nodeInfo.title, this.nodeViewProperty.nodeIndex,
            ItemTreeNode.TreeNodePreferences(
                this.nodePosition.xPosition,
                this.nodePosition.yPosition,
                this.nodeViewProperty.width,
                this.nodeViewProperty.height
            )
        )
    )
    this.childs.forEach {
        nodeData.addAll(it.toLazyTreeMutableList())
    }
    return nodeData
}