package com.digitalsamurai.graphlib.ui.libs.vm

import com.digitalsamurai.graphlib.database.room.libs.Lib
import kotlinx.coroutines.flow.Flow

interface LibsScreenViewModelUI {

    val libsState: Flow<List<Lib>>

    fun selectLib(libName : String)
}