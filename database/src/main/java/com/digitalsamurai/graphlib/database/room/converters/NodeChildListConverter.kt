package com.digitalsamurai.graphlib.database.room.converters

import androidx.room.TypeConverter
import com.digitalsamurai.graphlib.database.room.nodes.node.entity.ChildNodes
import com.google.gson.Gson

class NodeChildListConverter {

    private var gson : Gson = Gson()

    @TypeConverter
    fun toDatabaseString(data : ChildNodes) : String{
        return gson.toJson(data)
    }
    @TypeConverter
    fun fromDatabaseString(data : String) : ChildNodes {
        return gson.fromJson(data, ChildNodes::class.java)
    }
}