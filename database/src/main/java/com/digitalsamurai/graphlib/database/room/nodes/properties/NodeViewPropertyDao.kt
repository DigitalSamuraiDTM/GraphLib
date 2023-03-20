package com.digitalsamurai.graphlib.database.room.nodes.properties

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.w3c.dom.Node

@Dao
interface NodeViewPropertyDao {

    @Insert
    suspend fun insert(nodeViewProperty : NodeViewProperty)

    @Delete
    suspend fun delete(nodeViewProperty: NodeViewProperty)

    @Update
    suspend fun update(nodeViewProperty: NodeViewProperty)

    @Query("SELECT * FROM ${NodeViewProperty.TABLE_NAME} WHERE ${NodeViewProperty.COLUMN_NODE_INDEX}=:nodeIndex")
    suspend fun getByIndex(nodeIndex : Long) : NodeViewProperty?

    @Query("SELECT * FROM ${NodeViewProperty.TABLE_NAME}")
    suspend fun getAll() : List<NodeViewProperty>
}