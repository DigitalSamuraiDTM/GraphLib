package com.digitalsamurai.graphlib.database.di

import com.digitalsamurai.graphlib.database.di.modules.DatabaseModule
import com.digitalsamurai.graphlib.database.room.converters.NodeChildListConverter
import com.digitalsamurai.graphlib.database.tree.TreeManager
import dagger.Component

@DatabaseScope
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {

    fun injectTreeManager(treeManager: TreeManager)

    fun injectNodeChildListConverter(nodeChildListConverter: NodeChildListConverter)
}