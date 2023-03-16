package com.digitalsamurai.graphlib.ui.custom.bottom_navigator

import androidx.compose.runtime.State
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorState
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorUi

interface BottomNavigatorViewModel {

    fun bottomNavigatorClicked(element : BottomNavigatorUi)


    val navigatorState : State<BottomNavigatorState>


}