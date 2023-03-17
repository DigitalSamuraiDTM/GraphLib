package com.digitalsamurai.graphlib.ui.libs

import androidx.lifecycle.ViewModel
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.preferences.GraphPreferences
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.database.room.libs.Lib
import com.digitalsamurai.graphlib.ui.libs.vm.LibsScreenViewModelUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LibsScreenViewModel : ViewModel(), LibsScreenViewModelUI {

    @Inject
    lateinit var database: GraphDatabase

    @Inject
    lateinit var pref : GraphPreferences

    override val libsState: Flow<List<Lib>>

    init {

    }


    init {
        GraphLibApp.appComponent.injectLibsViewModel(this)

        libsState = database.libDao().observeAll()
    }


    override fun selectLib(libName: String) {
        pref.lastOpenedGraph = libName
    }

    private var selectedLibJob: Job? = null


    fun getAll() {

    }
}