package com.digitalsamurai.graphlib.ui.main.vm

import android.graphics.PointF
import androidx.compose.runtime.State
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.BottomNavigatorViewModel
import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode
import com.digitalsamurai.graphlib.ui.main.MainScreenState

interface MainViewModelUI : BottomNavigatorViewModel, NodeViewModel {

    val library : State<String>


    fun updateFullScreenState(isFullScreen : Boolean? = null)
    fun clickNavigationBottomButton()
    fun treeClick(coordinate: PointF)

    val isFullScreen : State<Boolean>
    val state : State<MainScreenState>

}