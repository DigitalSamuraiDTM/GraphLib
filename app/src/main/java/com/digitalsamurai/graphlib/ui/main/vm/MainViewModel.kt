package com.digitalsamurai.graphlib.ui.main.vm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.layout.Layout
import androidx.lifecycle.ViewModel
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.preferences.GraphPreferences
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel : ViewModel(), MainViewModelUI {



    @Inject
    lateinit var preferences : GraphPreferences

    @Inject
    lateinit var database: GraphDatabase

    private var _library = mutableStateOf<String>("")
    override val library: State<String>
        get() =_library

    init {
        GraphLibApp.mainComponent.injectMainViewModel(this)
    }




    fun initData(libName : String?){
        libName?.let {
            _library.value = libName
        }
        preferences.lastOpenedGraph = libName


    }

}
@Composable
fun ReverseFlowRow(
    content: @Composable () -> Unit
) = Layout(content) { measurables, constraints ->
    // measuring children, layout sizing, and placing children takes place here.

    // 1. The measuring phase.
    val placeables = measurables.map { measurable ->
        measurable.measure(constraints)
    }

    // 2. The sizing phase.
    layout(constraints.maxWidth, constraints.maxHeight) {
        // 3. The placement phase.
        var yPosition = 0
        var xPosition = constraints.maxWidth

        placeables.forEach { placeable ->
            //fak
        }
    }

}