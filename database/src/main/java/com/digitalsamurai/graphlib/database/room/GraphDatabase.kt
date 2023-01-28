package com.digitalsamurai.graphlib.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.digitalsamurai.graphlib.database.room.converters.LocalDateTimeConverter
import com.digitalsamurai.graphlib.database.room.converters.NodeChildListConverter
import com.digitalsamurai.graphlib.database.room.libs.Lib
import com.digitalsamurai.graphlib.database.room.libs.LibDao
import com.digitalsamurai.graphlib.database.room.nodes.LibNode
import com.digitalsamurai.graphlib.database.room.nodes.LibNodeDao

@Database(entities = [Lib::class,LibNode::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class, NodeChildListConverter::class)
abstract class GraphDatabase : RoomDatabase() {

    abstract fun libDao() : LibDao

    abstract fun libNodeDao() : LibNodeDao

}