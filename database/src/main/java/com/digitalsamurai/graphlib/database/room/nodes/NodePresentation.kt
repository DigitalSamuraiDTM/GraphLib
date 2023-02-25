package com.digitalsamurai.graphlib.database.room.nodes

import androidx.room.Embedded
import androidx.room.Relation
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode
import com.digitalsamurai.graphlib.database.room.nodes.position.NodePosition
import com.digitalsamurai.graphlib.database.room.nodes.properties.NodeViewProperty

/**
 * [NodePresentation] provide all general data about node in presentation layer.
 * Presentation layer expand this class the composable function for drawing in Compose layout
 * */
data class NodePresentation(

    var nodeInfo : LibNode,

    var nodePosition: NodePosition = defaultNodePosition(nodeInfo.nodeIndex),

    var nodeViewProperty: NodeViewProperty = defaultNodeViewProperty(nodeInfo.nodeIndex),

    var childs : List<NodePresentation> = emptyList()
)  {





    companion object{
        fun defaultNodePosition(nodeIndex : Long) = NodePosition(nodeIndex = nodeIndex,0,0)
        fun defaultNodeViewProperty(nodeIndex : Long) = NodeViewProperty(nodeIndex = nodeIndex,NodeViewProperty.DEFAULT_WIDTH,NodeViewProperty.DEFAULT_HEIGHT)
    }
    class Builder(private val nodeInfo: LibNode){

        private var nodePosition = defaultNodePosition(nodeInfo.nodeIndex)
        private var nodeViewProperty = defaultNodeViewProperty(nodeInfo.nodeIndex)
        private var childs : List<NodePresentation> = emptyList()


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

        fun setChilds(childs : List<NodePresentation>?) : Builder{
            childs?.let {
                this.childs = childs
            }
            return this
        }

        fun build() : NodePresentation{
            return NodePresentation(nodeInfo,nodePosition,nodeViewProperty, childs)
        }
    }
}