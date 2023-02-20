package com.digitalsamurai.graphlib.ui.start.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.preferences.GraphPreferences
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.ui.start.event.StartClickEvents
import com.digitalsamurai.graphlib.ui.start.states.StartScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel : ViewModel() {

    @Inject
    lateinit var preferences : GraphPreferences

    @Inject
    lateinit var database : GraphDatabase

    private val _state = mutableStateOf(StartScreenState(null))
    val state : State<StartScreenState>
    get() {
        _state.value = StartScreenState(preferences.lastOpenedGraph)
        return _state
    }
    init{
        GraphLibApp.startComponent.injectStartViewModel(this)
        viewModelScope.launch {
            _state.value = StartScreenState(preferences.lastOpenedGraph,database.libDao().countLibs()!=0)
        }
    }

    fun clickEvent(event : StartClickEvents){
        when(event){
            StartClickEvents.NEW_OR_OTHER -> {
                //can handle click event
            }
        }
    }

}