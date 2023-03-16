package com.digitalsamurai.graphlib.ui.main.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.preferences.GraphPreferences
import com.digitalsamurai.graphlib.database.tree.TreeManager
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorState
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel : ViewModel(), MainViewModelUI {



    @Inject
    lateinit var preferences : GraphPreferences



    @Inject
    lateinit var treeManagerFactory: TreeManager.Factory
    lateinit var treeManager: TreeManager

    private var _library = mutableStateOf<String>("")
    override val library: State<String>
        get() =_library

    init {
        GraphLibApp.mainComponent.injectMainViewModel(this@MainViewModel)
    }


    //MAIN VIEWMODEL UI


    private val _focusedElement = mutableStateOf<Long?>(null)
    override val focusedElement: State<Long?>
        get() = _focusedElement



    private val _isFullScreen = mutableStateOf(false)
    override val isFullScreen: State<Boolean>
        get() = _isFullScreen

    override fun updateFullScreenState(isFullScreen: Boolean?) {
        isFullScreen?.let {
            _isFullScreen.value = it
            return
        }
        _isFullScreen.value = !_isFullScreen.value
    }

    fun initData(libName : String?){
        viewModelScope.launch {
            libName?.let {
                _library.value = libName
                treeManager = treeManagerFactory.build(it)
                initTree()
            }

        }
        preferences.lastOpenedGraph = libName


    }

    private fun initTree(){
        viewModelScope.launch {
            val rootNode = treeManager.getRootNode()

        }
    }


    //BOTTOM NAVIGATOR

    override fun bottomNavigatorClicked(element: BottomNavigatorUi) {
        TODO("Not yet implemented")
    }

    override val navigatorState: State<BottomNavigatorState>
        get() = TODO("Not yet implemented")
}


