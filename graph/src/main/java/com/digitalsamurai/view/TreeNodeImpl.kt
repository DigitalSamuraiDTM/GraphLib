package com.digitalsamurai.view

import com.digitalsamurai.tree.TreeNode


class TreeNodeImpl private constructor(override var data: ViewNode,
                                       override var childNodes: ArrayList<TreeNode<ViewNode>>,
                                       override var parentNode: TreeNode<ViewNode>
) : TreeNode<ViewNode> {
    override fun addNode(data: ViewNode): TreeNode<ViewNode> {
        val n = TreeNodeImpl(data,ArrayList(),this)
        childNodes.add(n)
        return n
    }
    class RootNode(
        override var data: ViewNode
    ) : com.digitalsamurai.graph.tree.RootNode<ViewNode> {
        override var parentNode: TreeNode<ViewNode>
            get() = this
            set(value) {}

        override var childNodes: ArrayList<TreeNode<ViewNode>> = ArrayList()


        override fun addNode(data: ViewNode): TreeNode<ViewNode> {
            val n = TreeNodeImpl(data,ArrayList(),this)
            childNodes.add(n)
            return n
        }
    }
}
