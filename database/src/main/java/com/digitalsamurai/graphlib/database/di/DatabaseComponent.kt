package com.digitalsamurai.graphlib.database.di

import androidx.room.Database
import com.digitalsamurai.graphlib.database.room.converters.NodeChildListConverter
import com.digitalsamurai.graphlib.database.tree.TreeManager
import dagger.Component
import dagger.Subcomponent

@DatabaseScope
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {

    fun injectTreeManager(treeManager: TreeManager)

    fun injectNodeChildListConverter(nodeChildListConverter: NodeChildListConverter)
}