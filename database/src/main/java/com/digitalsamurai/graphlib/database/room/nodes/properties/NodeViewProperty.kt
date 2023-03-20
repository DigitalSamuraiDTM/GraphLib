package com.digitalsamurai.graphlib.database.room.nodes.properties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode

@Entity(tableName = NodeViewProperty.TABLE_NAME,
        foreignKeys = [
                ForeignKey(
                        onDelete = ForeignKey.CASCADE,
                        entity = LibNode::class,
                        parentColumns = [LibNode.COLUMN_NODE_INDEX],
                        childColumns = [NodeViewProperty.COLUMN_NODE_INDEX])],
)
data class NodeViewProperty (

        @PrimaryKey
        @ColumnInfo(name = COLUMN_NODE_INDEX)
        val nodeIndex : Long,

        @ColumnInfo(name = COLUMN_WIDTH)
        val width : Int = DEFAULT_WIDTH,

        @ColumnInfo(name = COLUMN_HEIGHT)
        val height : Int = DEFAULT_HEIGHT,

        ){
        companion object{
                const val TABLE_NAME = "node_property"
                const val COLUMN_NODE_INDEX = "node_index"
                const val COLUMN_WIDTH = "node_width"
                const val COLUMN_HEIGHT = "node_height"

                const val DEFAULT_WIDTH = 100
                const val DEFAULT_HEIGHT = 100
        }
}