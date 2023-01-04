package com.digitalsamurai.graphlib.custom

import android.content.Context
import com.digitalsamurai.math.tree.RootNode
import com.digitalsamurai.math.tree.Tree
import com.digitalsamurai.graphlib.math.tree.TreeNode
import com.digitalsamurai.math.tree.extensions.NodeNotInTreeException

class TreeView(private val context : Context,
               override val rootNode: RootNode<ViewNode>) : Tree<ViewNode> {



    var view : LinearCoordinatesLayout = LinearCoordinatesLayout(context,this)

    init {
        setRootNode()
    }

    private fun setRootNode(){
        view.CoordinatesPosition(rootNode.data).addViewInLayout()
    }

    override fun addNode(node: TreeNode<ViewNode>, parent: TreeNode<ViewNode>) {
        parent.childNodes.add(node)
        view.CoordinatesPosition(node.data.viewPosition,node.data).addViewInLayout()
    }

    override fun addNode(node: TreeNode<ViewNode>) {
        view.CoordinatesPosition(node.data.viewPosition,node.data).addViewInLayout()
    }

    override fun addNode(data: ViewNode, parent: TreeNode<ViewNode>) : TreeNode<ViewNode> {
        val node = parent.addNode(data)
        addNode(node)
        return node
    }

    override fun addNode(node: TreeNode<ViewNode>, parentIndex: Int) {
        //TODO find node by index and add
        throw NodeNotInTreeException()
    }

    override fun removeNodeRecursively(node: TreeNode<ViewNode>): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeNodeRecursively(nodeIndex: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getTreeDepth(): Int {
        TODO("Not yet implemented")
    }

    override fun getNode(nodeIndex: Int): TreeNode<ViewNode> {
        TODO("Not yet implemented")
    }

    override fun isNodeInTree(node: TreeNode<ViewNode>): Boolean {
        TODO("Not yet implemented")
    }

    override fun getNodeList(): List<TreeNode<ViewNode>> {
        val nodeList = ArrayList<TreeNode<ViewNode>>()
        getNodeListByNode(nodeList,rootNode)
        return nodeList
    }

    private fun getNodeListByNode(nodeList : ArrayList<TreeNode<ViewNode>>,node : TreeNode<ViewNode>) {
        nodeList.add(node)
        node.childNodes.forEach {
            if (it.isLeaf){
                nodeList.add(it)
            } else{
                getNodeListByNode(nodeList,it)
            }
        }
    }
}