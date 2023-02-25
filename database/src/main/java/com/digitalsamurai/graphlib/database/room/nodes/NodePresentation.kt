package com.digitalsamurai.graphlib.database.room.nodes

import androidx.room.Embedded
import androidx.room.Relation
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode
import com.digitalsamurai.graphlib.database.room.nodes.position.NodePosition
import com.digitalsamurai.graphlib.database.room.nodes.properties.NodeViewProperty

data class NodePresentation(

    var nodeInfo : LibNode,

    var nodePosition: NodePosition? = defaultNodePosition(nodeInfo.nodeIndex),

    var nodeViewProperty: NodeViewProperty? = defaultNodeViewProperty(nodeInfo.nodeIndex)
)  {

    companion object{
        fun defaultNodePosition(nodeIndex : Long) = NodePosition(nodeIndex = nodeIndex,0,0)
        fun defaultNodeViewProperty(nodeIndex : Long) = NodeViewProperty(nodeIndex = nodeIndex,NodeViewProperty.DEFAULT_WIDTH,NodeViewProperty.DEFAULT_HEIGHT)
    }
}