package com.digitalsamurai.graphlib.database.room.libs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LibDao {

    @Query("SELECT * FROM ${Lib.TABLE_NAME}")
    suspend fun selectAll() : List<Lib>

    @Query("SELECT * FROM ${Lib.TABLE_NAME} WHERE ${Lib.COLUMN_LIB_NAME} = :name")
    suspend fun findLibByName(name : String) : List<Lib>

    @Query("SELECT COUNT() FROM ${Lib.TABLE_NAME}")
    suspend fun countLibs() : Int

    @Query("SELECT * FROM ${Lib.TABLE_NAME}")
    fun observeAll() : Flow<List<Lib>>

    @Insert
    suspend fun insertLib(lib : Lib)

    @Delete
    suspend fun deleteLib(lib : Lib)
}