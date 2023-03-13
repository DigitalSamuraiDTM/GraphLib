package com.digitalsamurai.graphlib.database.room.nodes

import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode
import com.digitalsamurai.graphlib.database.room.nodes.position.NodePosition
import com.digitalsamurai.graphlib.database.room.nodes.properties.NodeViewProperty

/**
 * [Node] provide all general data about node in presentation layer.
 * Presentation layer expand this class the composable function for drawing in Compose layout
 * */
data class Node(

    var nodeInfo : LibNode,

    var nodePosition: NodePosition = defaultNodePosition(nodeInfo.nodeIndex),

    var nodeViewProperty: NodeViewProperty = defaultNodeViewProperty(nodeInfo.nodeIndex),

    var childs : List<Node> = emptyList(),

    //null when node is root node
    var parentNode : Node? = null
)  {


    /**
     * Function need for updating parent node when we finally create parent node and we can set parent node
     */
    fun updateParentNode(parentNode: Node){
        this.parentNode = parentNode
    }


    companion object{
        fun defaultNodePosition(nodeIndex : Long) = NodePosition(nodeIndex = nodeIndex,0,0)
        fun defaultNodeViewProperty(nodeIndex : Long) = NodeViewProperty(nodeIndex = nodeIndex,NodeViewProperty.DEFAULT_WIDTH,NodeViewProperty.DEFAULT_HEIGHT)
    }
    class Builder(private val nodeInfo: LibNode){

        private var nodePosition = defaultNodePosition(nodeInfo.nodeIndex)
        private var nodeViewProperty = defaultNodeViewProperty(nodeInfo.nodeIndex)
        private var childs : List<Node> = emptyList()
        private var parentNode : Node? = null

        fun setNodePosition(nodePosition: NodePosition?) : Builder {
            nodePosition?.let {
                this.nodePosition = nodePosition
            }
            return this
        }
        fun setNodeViewProperty(nodeViewProperty: NodeViewProperty?) : Builder{
            nodeViewProperty?.let {
                this.nodeViewProperty = nodeViewProperty
            }
            return this
        }

        fun setChilds(childs : List<Node>?) : Builder{
            childs?.let {
                this.childs = childs
            }
            return this
        }
        fun setParentNode(parentNode: Node?) : Builder{
            this.parentNode = parentNode
            return this
        }

        fun build() : Node{
            return Node(nodeInfo,nodePosition,nodeViewProperty, childs,parentNode)
        }
    }
}