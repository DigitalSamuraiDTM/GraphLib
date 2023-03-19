package com.digitalsamurai.graphlib.ui.main.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
                initTree()
                //инициализируем дерево из менеджера, кастуя к списку TreeNodeData для отображения на layout
                nodeDataList = (treeManager.getRootNode()?.toLazyTreeMutableList() ?: mutableListOf())


                //Переходим в состояние создание Root ноды
                if (nodeDataList.isEmpty()){
                    _state.value = MainScreenState.NewNode(nodeDataList,null,null,null)
                }

            }
            //если ничего не помогло, то белый экран
        }

        initNodeInfoListener()
    }

    private fun initNodeInfoListener(){
        nodeInfoMediatorGetter.setUpdateTitleListener(object : NodeInfoMediatorGetter.UpdateTitleListener{
            override fun onUpdate(title: String) {
                _state.value = MainScreenState.NewNode(emptyList(),title,null,null)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
    }

    /**
     * ----------------------[MainViewModelUI]---------------------
     * */


    private val _state = mutableStateOf<MainScreenState>(MainScreenState.Main(emptyList(),null))
    override val state: State<MainScreenState>
        get() = _state

//    private val _nodes = mutableStateListOf<ItemTreeNode.TreeNodeData>()
//    override val nodes: List<ItemTreeNode.TreeNodeData>
//        get() = _nodes

    private val _isFullScreen = mutableStateOf(false)
    override val isFullScreen: State<Boolean>
        get() = _isFullScreen


    override fun clickNavigationBottomButton() {



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


