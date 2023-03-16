package com.digitalsamurai.graphlib.ui.main.vm

import androidx.compose.runtime.State
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.BottomNavigatorViewModel

interface MainViewModelUI : BottomNavigatorViewModel {

    val library : State<String>

    fun updateFullScreenState(isFullScreen : Boolean? = null)
    val isFullScreen : State<Boolean>
    val focusedElement : State<Long?>

}