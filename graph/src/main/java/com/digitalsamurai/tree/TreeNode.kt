package com.digitalsamurai.tree

interface TreeNode<T> {
    var data : T

    var childNodes: ArrayList<TreeNode<T>>

    var parentNode : TreeNode<T>

    val isLeaf : Boolean
    get() = childNodes.isEmpty()

    fun addNode(data : T) : TreeNode<T>




}
