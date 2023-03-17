package com.digitalsamurai.graphlib.ui.main.vm

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Update
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.preferences.GraphPreferences
import com.digitalsamurai.graphlib.database.tree.TreeManager
import com.digitalsamurai.graphlib.di.general.MainScope
import com.digitalsamurai.graphlib.logic.mediator.NodeInfoMediator
import com.digitalsamurai.graphlib.logic.mediator.NodeInfoMediatorGetter
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.BottomNavigatorViewModel
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorState
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorUi
import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode
import com.digitalsamurai.graphlib.ui.main.MainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@MainScope
@HiltViewModel
class MainViewModel : ViewModel(), MainViewModelUI {


    @Inject
    lateinit var preferences: GraphPreferences


    @Inject
    lateinit var nodeInfoMediatorGetter: NodeInfoMediator

    @Inject
    lateinit var treeManagerFactory: TreeManager.Factory
    lateinit var treeManager: TreeManager

    private var _library = mutableStateOf<String>("")
    override val library: State<String>
        get() = _library

    init {
        GraphLibApp.getMainComponent().injectMainViewModel(this@MainViewModel)

        //проводим инициализацию графа непосредственно в конструкторе во избежание переинциализации дерева
        //в случае рекомпозиции экрана, тем самым уменьшаем нагрузку

        preferences.lastOpenedGraph?.let {
            _library.value = it
            preferences.lastOpenedGraph = it
            //инициализируем название библиотеки и асинхронно начинаем загружать дерево из бд
            viewModelScope.launch {
                treeManager = treeManagerFactory.build(it)
                initTree()
            }
            //если ничего не помогло, то белый экран
        }

        initNodeInfoListener()
    }

    private fun initNodeInfoListener(){
        nodeInfoMediatorGetter.setUpdateTitleListener(object : NodeInfoMediatorGetter.UpdateTitleListener{
            override fun onUpdate(title: String) {
                Log.d("GRAPH", "TITLE IS: ${title}")
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
    }

    /**
     * ----------------------[MainViewModelUI]---------------------
     * */


    private val _state = mutableStateOf<MainScreenState>(MainScreenState.Main())
    override val state: State<MainScreenState>
        get() = _state

    private val _nodes = mutableStateListOf<ItemTreeNode.TreeNodeData>()
    override val nodes: List<ItemTreeNode.TreeNodeData>
        get() = _nodes

    private val _isFullScreen = mutableStateOf(false)
    override val isFullScreen: State<Boolean>
        get() = _isFullScreen


    override fun clickBottomButton() {
        when (state.value) {
            is MainScreenState.Main -> {
                _state.value = MainScreenState.NewNode(null)
            }
            is MainScreenState.NewNode -> {
                //TODO finish create
            }
        }
    }

    override fun updateFullScreenState(isFullScreen: Boolean?) {
        isFullScreen?.let {
            _isFullScreen.value = it
            return
        }
        _isFullScreen.value = !_isFullScreen.value
    }

    //Правильно ли, что при каждой рекомпозиции initData вызывается каждый раз и инициализация происходит каждый раз?
    fun initData() {

        val nodeName = nodeInfoMediatorGetter.title
        val a = nodeName
    }

    private fun initTree() {

    }

    /**
     * ----------------------[NodeViewModel]---------------------
     * */

    override fun clickEvent(nodeIndex: Long) {
        TODO("Not yet implemented")
    }


    /**
     * ----------------------[BottomNavigatorViewModel]---------------------
     * */

    override fun bottomNavigatorClicked(element: BottomNavigatorUi) {
        TODO("Not yet implemented")
    }


    override val navigatorState: State<BottomNavigatorState>
        get() = TODO("Not yet implemented")
}


