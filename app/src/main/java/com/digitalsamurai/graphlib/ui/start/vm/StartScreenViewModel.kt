package com.digitalsamurai.graphlib.ui.start.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.preferences.GraphPreferences
import com.digitalsamurai.graphlib.ui.start.event.StartClickEvents
import com.digitalsamurai.graphlib.ui.start.states.StartScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel : ViewModel() {

    @Inject
    lateinit var preferences : GraphPreferences

    private val _state = mutableStateOf(StartScreenState(null))
    val state : State<StartScreenState>
    get() {
        _state.value = StartScreenState(preferences.lastOpenedGraph)
        return _state
    }
    init{
        GraphLibApp.startComponent.injectStartViewModel(this)
    }

    fun clickEvent(event : StartClickEvents){
        when(event){
            StartClickEvents.NEW_OR_OTHER -> {
                //can handle click event
            }
        }
    }

}