package com.digitalsamurai.graphlib

import com.digitalsamurai.graphlib.custom.ViewNode
import com.digitalsamurai.graphlib.math.tree.TreeNode

interface CreateInterface {
    fun created(x: Int,y : Int,text : String, parent : TreeNode<ViewNode>)
}