package com.digitalsamurai.graphlib.ui.createnode.vm

import androidx.compose.runtime.State
import com.digitalsamurai.graphlib.database.room.nodes.position.NodePosition
import com.digitalsamurai.graphlib.logic.mediator.NodeInfoMediator
import com.digitalsamurai.graphlib.ui.main.MainScreenState

interface CreateNodeViewModelUI {

    val title : State<String>

    fun insertNodeTitle(title : String)

    /**
     * Подверждение того, что титул был выбран и мы можем запушить его в [NodeInfoMediator]
     * */
    fun applyNodeNameMediator()

}
