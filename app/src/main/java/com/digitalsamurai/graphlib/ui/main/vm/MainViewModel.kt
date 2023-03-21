package com.digitalsamurai.graphlib.ui.main.vm

import android.graphics.PointF
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.toPoint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.preferences.GraphPreferences
import com.digitalsamurai.graphlib.database.tree.TreeManager
import com.digitalsamurai.graphlib.di.general.MainScope
import com.digitalsamurai.graphlib.logic.createnode.NodeBuilder
import com.digitalsamurai.graphlib.logic.mappers.toLazyTreeMutableList
import com.digitalsamurai.graphlib.logic.mediator.NodeInfoMediator
import com.digitalsamurai.graphlib.logic.mediator.NodeInfoMediatorGetter
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.BottomNavigatorViewModel
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorState
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.entity.BottomNavigatorUi
import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode
import com.digitalsamurai.graphlib.ui.main.MainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@MainScope
@HiltViewModel
class MainViewModel : ViewModel(), MainViewModelUI, ReturnController {


    @Inject
    lateinit var preferences: GraphPreferences


    @Inject
    lateinit var nodeInfoMediatorGetter: NodeInfoMediator

    @Inject
    lateinit var treeManagerFactory: TreeManager.Factory
    lateinit var treeManager: TreeManager

    //канал для навигации
    private val _navigationChannel =
        Channel<String>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val navigationChannel: ReceiveChannel<String>
        get() = _navigationChannel

    var nodeBuilder: NodeBuilder? = null

    /**
     * Хранит всё, что надо для отображения на [LazyTreeLayout]
     * */
    private var nodeDataList = mutableListOf<ItemTreeNode.TreeNodeData>()


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

                observeTreeChanges()
                //инициализируем дерево из менеджера, кастуя к списку TreeNodeData для отображения на layout
                nodeDataList =
                    (treeManager.getRootNode()?.toLazyTreeMutableList() ?: mutableListOf())


                //Переходим в состояние создание Root ноды. Передаём управление NodeBuilder
                if (nodeDataList.isEmpty()) {
                    nodeBuilder = NodeBuilder(
                        library.value,
                        this@MainViewModel as ReturnController,
                        viewModelScope,
                        nodeDataList,
                        _navigationChannel,
                        _state,
                        treeManager
                    )
                }
            }
            //если ничего не помогло, то белый экран
        }

        initNodeInfoListener()
    }

    private fun observeTreeChanges() {
        viewModelScope.launch {
            treeManager.flowTreeState().map { it?.toLazyTreeMutableList() ?: emptyList() }.collect {
                nodeDataList.apply {
                    clear()
                    addAll(it)
                }
                if (_state.value is MainScreenState.Main){
                    _state.value = MainScreenState.Main(nodeDataList,null)
                }
            }

        }
    }

    private fun initNodeInfoListener() {
        nodeInfoMediatorGetter.setUpdateTitleListener(object :
            NodeInfoMediatorGetter.UpdateTitleListener {
            override fun onUpdate(title: String) {
                nodeBuilder?.setTitle(title)
            }
        })
    }


    override fun onCleared() {
        super.onCleared()
    }

    /**
     * ----------------------[ReturnController]---------------------
     * */

    override fun returnControl() {
        when (_state.value) {
            //Управление не было передано
            is MainScreenState.Main -> {

            }

            //NodeBuilder возвращает состояние управление, т.к. он завершил работу
            is MainScreenState.NewNode -> {
                Log.d("GRAPH", "NEW NODE CONTROL RETURN")
                _state.value = MainScreenState.Main(nodeDataList, null)
            }
        }
    }

    /**
     * ----------------------[MainViewModelUI]---------------------
     * */


    private val _state = mutableStateOf<MainScreenState>(MainScreenState.Main(emptyList(), null))
    override val state: State<MainScreenState>
        get() = _state

//    private val _nodes = mutableStateListOf<ItemTreeNode.TreeNodeData>()
//    override val nodes: List<ItemTreeNode.TreeNodeData>
//        get() = _nodes

    private val _isFullScreen = mutableStateOf(false)
    override val isFullScreen: State<Boolean>
        get() = _isFullScreen


    override fun clickNavigationBottomButton() {
        if (_state.value is MainScreenState.NewNode) {
            nodeBuilder?.clickNavigationBottomButton()
        }
    }

    override fun treeClick(coordinate: PointF) {
        //когда наше состояние - создание новой ноды, то мы передаём все события в конструктор
        if (state.value is MainScreenState.NewNode) {
            nodeBuilder?.coordinateClicked(coordinate.toPoint())
            return
        }

        //TODO calculate)
    }

    override fun updateFullScreenState(isFullScreen: Boolean?) {
        isFullScreen?.let {
            _isFullScreen.value = it
            return
        }
        _isFullScreen.value = !_isFullScreen.value
    }


    private fun initTree() {

    }

    /**
     * ----------------------[NodeViewModel]---------------------
     * */

    override fun clickEvent(nodeIndex: Long) {
        if (state.value is MainScreenState.NewNode) {
            nodeBuilder?.nodeClicked(nodeIndex)
        }
    }


    /**
     * ----------------------[BottomNavigatorViewModel]---------------------
     * */


    override fun bottomNavigatorClicked(element: BottomNavigatorUi) {

    }


    override val navigatorState: State<BottomNavigatorState>
        get() = TODO("Not yet implemented")
}


