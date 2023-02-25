package com.digitalsamurai.graphlib.database.room.nodes.position

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NodePositionDao {

    @Insert
    suspend fun insertNodePosition(nodePosition: NodePosition)

    @Delete
    suspend fun deleteNodePosition(nodePosition: NodePosition)

    @Query("SELECT * FROM ${NodePosition.TABLE_NAME} WHERE ${NodePosition.COLUMN_NODE_INDEX}=:nodeIndex")
    suspend fun getNodeByIndex(nodeIndex: kotlin.Long) : NodePosition?

    @Update
    suspend fun updateNodePosition(nodePosition: NodePosition)


}