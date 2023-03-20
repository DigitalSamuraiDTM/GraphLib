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
                        childColumns = [LibNode.COLUMN_LIB])],
        indices = [Index(LibNode.COLUMN_NODE_INDEX)]
)
data class LibNode (

        //FOREIGN
        @ColumnInfo(name = COLUMN_LIB)
        val lib : String,


        @ColumnInfo(name = COLUMN_TITLE)
        var title : String,

        //IF NULL -> that node is root node
        @ColumnInfo(name = COLUMN_PARENT_INDEX)
        val parentIndex : Long?,

        @ColumnInfo(name = COLUMN_CHILDS)
        val childs : ChildNodes = ChildNodes(),

        @ColumnInfo(name = COLUMN_NODE_INDEX)
        @PrimaryKey(autoGenerate = true)
        var nodeIndex : Long = 0L,

        ){

        companion object{
                const val COLUMN_PARENT_INDEX = "parent_index"
                const val COLUMN_CHILDS = "node_childs"
                const val COLUMN_TITLE = "node_title"
                const val TABLE_NAME = "lib_nodes"
                const val COLUMN_NODE_INDEX = "node_index"
                const val COLUMN_LIB = "library_name"
        }
}