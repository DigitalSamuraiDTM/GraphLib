package com.digitalsamurai.graphlib.database.room.nodes.node

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.digitalsamurai.graphlib.database.room.libs.Lib
import com.digitalsamurai.graphlib.database.room.nodes.node.entity.ChildNodes

@Entity(tableName = LibNode.TABLE_NAME,
        foreignKeys = [
                ForeignKey(
                        onDelete = CASCADE,
                        entity = Lib::class,
                        parentColumns = [Lib.COLUMN_LIB_NAME],
                        childColumns = [LibNode.COLUMN_PARENT_LIB_NAME])],
        indices = [Index(LibNode.COLUMN_NODE_PRIMARY_INDEX)]
)
data class LibNode (

        //FOREIGN
        @ColumnInfo(name = COLUMN_PARENT_LIB_NAME)
        val lib : String,


        @ColumnInfo(name = COLUMN_NODE_TITLE)
        val title : String,

        //IF NULL -> that node is root node
        @ColumnInfo(name = COLUMN_NODE_INDEX_PRIMARY)
        val parentIndex : Long?,

        @ColumnInfo(name = COLUMN_NODE_IS_ROOT)
        val childs : ChildNodes,

        @ColumnInfo(name = COLUMN_NODE_PRIMARY_INDEX)
        @PrimaryKey(autoGenerate = true)
        val nodeIndex : Long,

        ){

        companion object{
                const val COLUMN_NODE_INDEX_PRIMARY = "parent_index"
                const val COLUMN_NODE_IS_ROOT = "node_childs"
                const val COLUMN_NODE_TITLE = "node_title"
                const val TABLE_NAME = "lib_nodes"
                const val COLUMN_NODE_PRIMARY_INDEX = "node_index"
                const val COLUMN_PARENT_LIB_NAME = "library_name"
        }
}