package com.digitalsamurai.math.tree

import com.digitalsamurai.graphlib.math.tree.TreeNode

interface RootNode<T> : TreeNode<T> {

    override var data: T

    override var childNodes: ArrayList<TreeNode<T>>


    override var parentNode: TreeNode<T>
        get() = this
        set(value) {}
    override val isLeaf: Boolean
        get() = false

}