package com.digitalsamurai.graphlib.logic.mediator

import kotlinx.coroutines.flow.StateFlow

interface NodeInfoMediatorGetter {
    fun getTitle(remove : Boolean) : String?
    fun setUpdateTitleListener(listener: UpdateTitleListener)
    interface UpdateTitleListener{
        fun onUpdate(title : String)
    }
}