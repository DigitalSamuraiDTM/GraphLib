package com.digitalsamurai.graphlib.database.room.nodes.node

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.digitalsamurai.graphlib.database.room.nodes.node.entity.ChildNodes
import com.digitalsamurai.graphlib.database.room.nodes.node.entity.NodeDeleteStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface LibNodeDao {


    @Query("SELECT * FROM ${LibNode.TABLE_NAME} WHERE ${LibNode.COLUMN_LIB}=:libname")
    suspend fun getAllByLib(libname : String) : List<LibNode>

    @Query("SELECT * FROM ${LibNode.TABLE_NAME} WHERE ${LibNode.COLUMN_LIB}=:libName")
    fun flowLibNode(libName : String) : Flow<List<LibNode>>

    @Transaction
    suspend fun insert(libNode: LibNode) : LibNode{
        val index = insertUnsafely(libNode)
        if (libNode.parentIndex!=null){
           getNodeByIndex(libNode.parentIndex)?.let {
               it.childs.childs.addAll(listOf(index))
               updateChilds(it.nodeIndex,it.childs)
               println(getNodeByIndex(it.nodeIndex))
           }
        }
        return libNode.also { it.nodeIndex = index }
    }


    @Insert
    suspend fun insertUnsafely(libNode: LibNode) : Long

    @Update
    suspend fun updateNode(libNode: LibNode)

    @Query("UPDATE ${LibNode.TABLE_NAME} SET ${LibNode.COLUMN_CHILDS}=:childs WHERE ${LibNode.COLUMN_NODE_INDEX}=:nodeIndex")
    suspend fun updateChilds(nodeIndex : Long, childs : ChildNodes)

    @Query("SELECT * FROM ${LibNode.TABLE_NAME} WHERE ${LibNode.COLUMN_NODE_INDEX}=:index")
    suspend fun getNodeByIndex(index: Long) : LibNode?

    @Query("SELECT * FROM ${LibNode.TABLE_NAME} WHERE ${LibNode.COLUMN_PARENT_INDEX} IS NULL AND ${LibNode.COLUMN_LIB}=:libName")
    suspend fun getRootNodeByLib(libName : String) : LibNode?

    /**
     * Use custom query request because auto-generated delete not worked
     * */
    @Query("DELETE FROM ${LibNode.TABLE_NAME} WHERE ${LibNode.COLUMN_NODE_INDEX}=:nodeIndex")
    suspend fun deleteUnsafely(nodeIndex : Long)

    @Transaction
    suspend fun delete(deleteStrategy : NodeDeleteStrategy, libNode : LibNode){
        when(deleteStrategy){
            NodeDeleteStrategy.CONNECT_CHILDREN_TO_PARENT -> {
                if (libNode.parentIndex!=null){
                    val parentNode = getNodeByIndex(libNode.parentIndex)
                    parentNode?.let {
                        parentNode.childs.childs.apply {
                            addAll(libNode.childs.childs)
                            remove(libNode.nodeIndex)
                        }
                        updateNode(parentNode)
                        deleteUnsafely(libNode.nodeIndex)
                        return
                    }
                    //need some log
                } else{
                    //delete root node
                    deleteUnsafely(libNode.nodeIndex)

                }
            }
        }
    }




}