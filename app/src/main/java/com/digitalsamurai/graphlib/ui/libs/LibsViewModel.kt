package com.digitalsamurai.graphlib.ui.libs

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.database.room.libs.Lib
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibsScreenViewModel : ViewModel() {

    @Inject
    lateinit var database: GraphDatabase


   val libsState : Flow<List<Lib>>

    init {

    }
    fun initLibsViewModel(){
    }

    init {
        GraphLibApp.appComponent.injectLibsViewModel(this)

        libsState = database.libDao().observeAll()
    }

    private var selectedLibJob : Job? = null


    fun getAll(){

    }
}