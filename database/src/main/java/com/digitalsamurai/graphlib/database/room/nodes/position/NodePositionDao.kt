package com.digitalsamurai.graphlib.database.room.nodes.position

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface NodePositionDao {

    @Insert
    suspend fun insertNodePosition(nodePosition: NodePosition)

    @Delete
    suspend fun deleteNodePosition(nodePosition: NodePosition)

    @Query("SELECT * FROM ${NodePosition.TABLE_NAME} WHERE ${NodePosition.COLUMN_NODE_INDEX}=:nodeIndex")
    suspend fun getNodeByIndex(nodeIndex : Int) : NodePosition

    @Update
    suspend fun updateNodePosition(nodePosition: NodePosition)


}