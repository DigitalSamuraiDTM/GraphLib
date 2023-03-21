package com.digitalsamurai.graphlib.database.room.nodes.position

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NodePositionDao {

    @Insert
    suspend fun insert(nodePosition: NodePosition)

    @Delete
    suspend fun delete(nodePosition: NodePosition)

    @Query("SELECT * FROM ${NodePosition.TABLE_NAME} WHERE ${NodePosition.COLUMN_NODE_INDEX}=:nodeIndex")
    suspend fun getNodeByIndex(nodeIndex: kotlin.Long) : NodePosition?

    @Query("SELECT * FROM ${NodePosition.TABLE_NAME}")
    suspend fun getAll() : List<NodePosition>

    @Update
    suspend fun updateNodePosition(nodePosition: NodePosition)


}