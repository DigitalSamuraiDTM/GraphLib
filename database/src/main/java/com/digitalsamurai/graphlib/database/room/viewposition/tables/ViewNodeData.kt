package com.digitalsamurai.graphlib.database.room.viewposition.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode

@Entity(tableName = ViewNodeData.TABLE_NAME,
        foreignKeys = [ForeignKey(
            onDelete = CASCADE,
            onUpdate = CASCADE,
            entity = LibNode::class,
            parentColumns = [LibNode.COLUMN_NODE_PRIMARY_INDEX],
            childColumns = [ViewNodeData.COLUMN_PARENT_INDEX])])
data class ViewNodeData(

    @ColumnInfo(name = COLUMN_PARENT_INDEX, index = true)
    val parentIndex : Long,

    @ColumnInfo(name = COLUMN_X_POS)
    val xPosition : Int,

    @ColumnInfo(name = COLUMN_Y_POS)
    val yPosition : Int,

    @ColumnInfo(name = COLUMN_VIEW_WIDTH)
    val viewWidth : Int,

    @ColumnInfo(name = COLUMN_VIEW_HEIGHT)
    val viewHeight : Int
){
    companion object{
        const val COLUMN_PARENT_INDEX = "libnode_parent_index"
        const val COLUMN_X_POS = "x_position"
        const val COLUMN_Y_POS = "y_position"
        const val COLUMN_VIEW_WIDTH = "width"
        const val COLUMN_VIEW_HEIGHT = "height"
        const val TABLE_NAME = "view_position"
    }
}
