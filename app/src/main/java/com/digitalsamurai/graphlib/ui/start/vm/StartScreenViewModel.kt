package com.digitalsamurai.graphlib.ui.start.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.digitalsamurai.graphlib.ui.start.states.StartScreenState
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class StartScreenViewModel : ViewModel() {

    private val _state = mutableStateOf(StartScreenState(null))
    val state : State<StartScreenState>
    get() = _state



}