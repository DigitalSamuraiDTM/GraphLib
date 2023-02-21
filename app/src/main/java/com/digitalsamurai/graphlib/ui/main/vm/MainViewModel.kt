package com.digitalsamurai.graphlib.ui.main.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.preferences.GraphPreferences
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel : ViewModel(), MainViewModelUI {



    @Inject
    lateinit var preferences : GraphPreferences


    private var _library = mutableStateOf<String>("")
    override val library: State<String>
        get() =_library

    init {
        GraphLibApp.mainComponent.injectMainViewModel(this)
    }




    fun initData(libName : String?){
        libName?.let {
            _library.value = libName
        }
        preferences.lastOpenedGraph = libName

        //todo init tree
    }

}