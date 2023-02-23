package com.digitalsamurai.graphlib.database.room.nodes.position

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.digitalsamurai.graphlib.database.room.libs.Lib
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode

@Entity(tableName = NodePosition.TABLE_NAME,
        foreignKeys = [
            ForeignKey(
            onDelete = ForeignKey.CASCADE,
            entity = LibNode::class,
            parentColumns = [LibNode.COLUMN_NODE_PRIMARY_INDEX],
            childColumns = [NodePosition.COLUMN_NODE_INDEX])],
)
data class NodePosition (
        @ColumnInfo(name = COLUMN_NODE_INDEX)
        val nodeIndex : Long,

        @ColumnInfo(name = COLUMN_X_POS)
        val xPosition : Int,

        @ColumnInfo(name = COLUMN_Y_POS)
        val yPosition : Int

        ){
    companion object{
        const val TABLE_NAME = "node_position"
        const val COLUMN_NODE_INDEX = "node_index"
        const val COLUMN_X_POS = "x_position"
        const val COLUMN_Y_POS = "y_position"
    }
}