package com.digitalsamurai.graphlib.ui.customscreen.bottom_navigator

import androidx.compose.runtime.State
import com.digitalsamurai.graphlib.database.room.nodes.NodePresentation
import com.digitalsamurai.graphlib.ui.customscreen.bottom_navigator.entity.BottomNavigatorState
import com.digitalsamurai.graphlib.ui.customscreen.bottom_navigator.entity.BottomNavigatorUi

interface BottomNavigatorViewModel {

    fun bottomNavigatorClicked(index : Long)


    val navigatorState : State<BottomNavigatorState>
}