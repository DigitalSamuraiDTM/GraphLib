package com.digitalsamurai.graph.tree

import com.digitalsamurai.tree.TreeNode


interface Tree<T> {

    val rootNode : RootNode<T>

    fun addNode(node : TreeNode<T>, parent : TreeNode<T>)
    fun addNode(node : TreeNode<T>, parentIndex : Int)
    fun addNode(node : TreeNode<T>)
    fun addNode(data : T, parent : TreeNode<T>) : TreeNode<T>

    fun removeNodeRecursively(node : TreeNode<T>) : Boolean
    fun removeNodeRecursively(nodeIndex : Int) : Boolean

    fun getTreeDepth() : Int

    fun getNode(nodeIndex : Int) : TreeNode<T>

    fun isNodeInTree(node : TreeNode<T>) : Boolean

    fun getNodeList() : List<TreeNode<T>>
}
