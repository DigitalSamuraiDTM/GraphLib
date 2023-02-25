package com.digitalsamurai.graphlib.database.di.factory

import com.digitalsamurai.graphlib.database.tree.TreeManager
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module
import dagger.assisted.AssistedFactory

//I try this and it looks like easy factory, but in building it has problems with @Generated annotation
// That's why faster and easier create manually factory

//@AssistedModule
//@Module(includes = [AssistedInject_FactoryModule::class])
//interface FactoryModule {
//
//
//
//
//    @AssistedFactory
//    interface TreeManagerFactory {
//        fun createTreeManager(libraryName : String) : TreeManager
//    }
//}