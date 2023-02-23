package com.digitalsamurai.graphlib.database.room.nodes.node

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.digitalsamurai.graphlib.database.room.nodes.node.entity.NodeDeleteStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface LibNodeDao {


    @Query("SELECT * FROM ${LibNode.TABLE_NAME} WHERE ${LibNode.COLUMN_PARENT_LIB_NAME}=:libname")
    suspend fun getAllByLib(libname : String) : List<LibNode>

    @Query("SELECT * FROM ${LibNode.TABLE_NAME} WHERE ${LibNode.COLUMN_PARENT_LIB_NAME}=:libName")
    fun flowLibNode(libName : String) : Flow<List<LibNode>>

    @Transaction
    suspend fun insert(libNode: LibNode) : Long{
        val index = insertUnsafely(libNode)
        if (libNode.parentIndex!=null){
            val parentNode = getNodeByIndex(libNode.parentIndex)
            parentNode.childs.childs.addAll(listOf(index.toInt()))
            updateNode(parentNode)
        }
        return index
    }


    @Insert
    suspend fun insertUnsafely(libNode: LibNode) : Long

    @Update
    fun updateNode(libNode: LibNode)

    @Query("SELECT * FROM ${LibNode.TABLE_NAME} WHERE ${LibNode.COLUMN_NODE_PRIMARY_INDEX}=:index")
    suspend fun getNodeByIndex(index: Long) : LibNode

    @Query("SELECT * FROM ${LibNode.TABLE_NAME} WHERE ${LibNode.COLUMN_NODE_PRIMARY_INDEX}=NULL AND ${LibNode.COLUMN_PARENT_LIB_NAME}=:libName")
    suspend fun getRootNodeByLib(libName : String) : LibNode

    @Delete
    suspend fun deleteUnsafely(vararg node: LibNode)

    @Transaction
    suspend fun delete(deleteStrategy : NodeDeleteStrategy, libNode : LibNode){
        when(deleteStrategy){
            NodeDeleteStrategy.CONNECT_CHILDREN_TO_PARENT -> {
                if (libNode.parentIndex!=null){
                    val parentNode = getNodeByIndex(libNode.parentIndex)
                    parentNode.childs.childs.addAll(libNode.childs.childs)
                    updateNode(parentNode)
                    deleteUnsafely(libNode)
                } else{
                    throw java.lang.Exception("Delete root node?")
                    //todo delete root node?
                }
            }
        }
    }




}