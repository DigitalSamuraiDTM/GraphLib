package com.digitalsamurai.graphlib.database.room.viewposition

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.digitalsamurai.graphlib.database.room.viewposition.tables.ViewNodeData

@Dao
interface ViewNodeDataDao {

    @Insert
    suspend fun insert(viewNodeData: ViewNodeData)

    @Update
    suspend fun update(viewNodeData: ViewNodeData)

    @Delete
    suspend fun delete(viewNodeData: ViewNodeData)

    @Query("SELECT * FROM ${ViewNodeData.TABLE_NAME} WHERE ${ViewNodeData.COLUMN_PARENT_INDEX}=:index")
    suspend fun getByIndex(index : Int) : ViewNodeData


}