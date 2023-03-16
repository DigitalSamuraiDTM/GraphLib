package com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity

class BottomNavigatorState(val prevoius : BottomNavigatorUi?,
                           val current : BottomNavigatorUi,
                           val next : List<BottomNavigatorUi>)
data class BottomNavigatorUi(
    val title : String,
    val nodeIndex : Long)