package com.digitalsamurai.graphlib.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.digitalsamurai.graphlib.database.room.converters.LocalDateTimeConverter
import com.digitalsamurai.graphlib.database.room.converters.NodeChildListConverter
import com.digitalsamurai.graphlib.database.room.libs.Lib
import com.digitalsamurai.graphlib.database.room.libs.LibDao
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNodeDao
import com.digitalsamurai.graphlib.database.room.nodes.position.NodePosition
import com.digitalsamurai.graphlib.database.room.nodes.position.NodePositionDao
import com.digitalsamurai.graphlib.database.room.nodes.properties.NodeViewProperty
import com.digitalsamurai.graphlib.database.room.nodes.properties.NodeViewPropertyDao

@Database(entities = [Lib::class, LibNode::class, NodePosition::class, NodeViewProperty::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class, NodeChildListConverter::class)
abstract class GraphDatabase : RoomDatabase() {

    abstract fun libDao() : LibDao

    abstract fun libNodeDao() : LibNodeDao

    abstract fun nodePosition() : NodePositionDao

    abstract fun nodeViewProperty() : NodeViewPropertyDao
}