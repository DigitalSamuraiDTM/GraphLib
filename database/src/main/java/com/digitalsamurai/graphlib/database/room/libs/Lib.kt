package com.digitalsamurai.graphlib.database.room.libs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.digitalsamurai.graphlib.database.room.converters.LocalDateTimeConverter
import java.time.LocalDateTime

@Entity(tableName = Lib.TABLE_NAME)
data class Lib(

    @PrimaryKey
    @ColumnInfo(name= COLUMN_LIB_NAME)
    val libName : String,

    @ColumnInfo(name="date_creating")
    @TypeConverters(LocalDateTimeConverter::class)
    val dateCreating : LocalDateTime

){
    companion object{
        const val TABLE_NAME = "graph_libs"
        const val COLUMN_LIB_NAME = "library_name"
    }
}