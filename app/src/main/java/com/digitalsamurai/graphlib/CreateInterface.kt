package com.digitalsamurai.graphlib

import com.digitalsamurai.tree.TreeNode
import com.digitalsamurai.view.ViewNode


interface CreateInterface {
    fun created(x: Int,y : Int,text : String, parent : TreeNode<ViewNode>)
}