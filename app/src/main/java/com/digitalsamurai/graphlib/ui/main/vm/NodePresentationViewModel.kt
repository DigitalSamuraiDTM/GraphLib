package com.digitalsamurai.graphlib.ui.main.vm

import com.digitalsamurai.graphlib.database.room.nodes.NodePresentation

interface NodePresentationViewModel {

    fun clickEvent(nodePresentation : NodePresentation)

}