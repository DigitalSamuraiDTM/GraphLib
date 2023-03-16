package com.digitalsamurai.graphlib.ui.custom.tree_layout

import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode
import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.TreeNodeContent

interface  TreeLayoutScope {

    fun items(data : List<ItemTreeNode.TreeNodeData>,
              itemContent : TreeNodeContent)
}

class TreeLayoutScopeImpl() : TreeLayoutScope{

    private val _items = mutableListOf<ItemTreeNode>()
    val items : List<ItemTreeNode>
    get() = _items

    override fun items(items: List<ItemTreeNode.TreeNodeData>, itemContent: TreeNodeContent) {
        items.forEach { this._items.add(ItemTreeNode(it,itemContent)) }
    }
}