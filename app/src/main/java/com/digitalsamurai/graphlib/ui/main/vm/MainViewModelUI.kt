package com.digitalsamurai.graphlib.ui.main.vm

import android.view.MotionEvent
import androidx.compose.runtime.State
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.BottomNavigatorViewModel
import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode

interface MainViewModelUI : BottomNavigatorViewModel, NodeViewModel {

    val library : State<String>

    fun updateFullScreenState(isFullScreen : Boolean? = null)

    val isFullScreen : State<Boolean>
    val focusedElement : State<Long?>
    val nodes : List<ItemTreeNode.TreeNodeData>

}