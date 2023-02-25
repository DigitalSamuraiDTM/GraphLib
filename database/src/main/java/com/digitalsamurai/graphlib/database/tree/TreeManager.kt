package com.digitalsamurai.graphlib.database.tree

import com.digitalsamurai.graphlib.database.di.DaggerDatabaseComponent

class TreeManager {

    init {
        val a = DaggerDatabaseComponent.create()
        val b = a
    }


}