package com.digitalsamurai.graphlib.ui.createnode.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.preferences.GraphPreferences
import com.digitalsamurai.graphlib.di.general.MainScope
import com.digitalsamurai.graphlib.logic.mediator.NodeInfoMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@MainScope
@HiltViewModel
class CreateNodeViewModel : ViewModel(), CreateNodeViewModelUI {

    @Inject
    lateinit var nodeInfoMediator: NodeInfoMediator

    @Inject
    lateinit var pref : GraphPreferences

    init {
        GraphLibApp.getMainComponent().injectCreateNodeViewModel(this)
    }


    override fun applyNodeNameMediator() {
        nodeInfoMediator.title = _title.value
    }

    override fun insertNodeTitle(title: String) {
        _title.value = title
    }
    private val _libName = mutableStateOf(pref.lastOpenedGraph ?: "")
    override val libName: State<String>
        get() = _libName

    private val _title = mutableStateOf("")
    override val title: State<String>
        get() = _title
}