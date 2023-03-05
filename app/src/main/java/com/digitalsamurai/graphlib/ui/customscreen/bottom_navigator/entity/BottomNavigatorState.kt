package com.digitalsamurai.graphlib.ui.customscreen.bottom_navigator.entity

sealed class BottomNavigatorState{
    object NoElements : BottomNavigatorState()
    class Root(val current : BottomNavigatorUi,val child : BottomNavigatorUi?) : BottomNavigatorState()
    class ChainElement(val parent : BottomNavigatorUi,val current : BottomNavigatorUi,val childs : List<BottomNavigatorUi>) : BottomNavigatorState()
    class LastElement(val parent : BottomNavigatorUi,val  current: BottomNavigatorUi) : BottomNavigatorState()
}
data class BottomNavigatorUi(
    val title : String,
    val nodeIndex : Long)