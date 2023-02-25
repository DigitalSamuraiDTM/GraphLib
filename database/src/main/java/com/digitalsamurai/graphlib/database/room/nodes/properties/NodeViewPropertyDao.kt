package com.digitalsamurai.graphlib.database.room.nodes.properties

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface NodeViewPropertyDao {

    @Insert
    suspend fun insert(nodeViewProperty : NodeViewProperty)

    @Delete
    suspend fun delete(nodeViewProperty: NodeViewProperty)

    @Update
    suspend fun update(nodeViewProperty: NodeViewProperty)


}