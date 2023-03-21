package com.digitalsamurai.graphlib.logic.createnode

import android.graphics.Point
import androidx.compose.runtime.MutableState
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode
import com.digitalsamurai.graphlib.database.room.nodes.position.NodePosition
import com.digitalsamurai.graphlib.database.room.nodes.properties.NodeViewProperty
import com.digitalsamurai.graphlib.database.tree.TreeManager
import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode
import com.digitalsamurai.graphlib.ui.main.MainScreenState
import com.digitalsamurai.graphlib.ui.main.vm.ReturnController
import com.digitalsamurai.graphlib.ui.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

/**
 * [NodeBuilder] делегирует на себя задачи создания новой записи (БЕЗ учёта начинки)
 * [MainViewModel] трекает кнопки нажатия создания новой записи и передаёт управление
 * Во время создания записи интерфейс имеет состояние [MainScreenState.NewNode]
 * Можно, конечно, довести этот класс до ООП паттерна "Цепочка обязанностей", но это слишком много кода, который, на данный момент, не даст никаких преимуществ
 * поэтому оставляю полукастомный шаблон "Конструктор" :)
 *
 * @param libName Нужна для создания ноды в бд
 * @param returnController сообщает о завершении управления состояниями (возвращаем управление в ViewModel)
 * @param viewModelScope Для создания ноды в бд
 * @param nodeList текущий список, отображающийся на экране
 * @param navigationChannel нужен для управления навигацией
 * @param uiState нужен для изменения состояний экрана
 * @param treeManager позволяет создать ноду
 * */
class NodeBuilder(
    private val libName: String,
    private val returnController: ReturnController,
    private val viewModelScope: CoroutineScope,
    private val nodeList: MutableList<ItemTreeNode.TreeNodeData>,
    private val navigationChannel: Channel<String>,
    private val uiState: MutableState<MainScreenState>,
    private val treeManager: TreeManager
) {

    //TODO изменение дефолтного размера и цвета узла

    private var nodePosition: Point? = null

    private var parentNode: Long? = null

    private var nodeTitle: String? = null


    init {
        //показываем кнопку с предложением создать ноду
        if (nodeList.isNotEmpty()) {
            //сразу навигация в создание заголовка
            navigationChannel.trySend(
                Screen.CreateNode.navigationWithArgs(Unit)
            )
        }
        this.uiState.value =
            MainScreenState.NewNode(
                nodeList,
                nodeList.isEmpty(),
                nodeTitle,
                parentNode,
                nodePosition
            )
    }


    fun setTitle(title: String) = apply {
        this.nodeTitle = title

        //Если текущий список узлов пустой, то мы априори создаем Root узел, а ему родитель не нужен
        //пропускаем выбор родителя и переходим к выбору позиции
        val isNeedParent: Long? = if (nodeList.isEmpty()) {
            -1
        } else {
            null
        }
        this.uiState.value =
            MainScreenState.NewNode(nodeList, nodeList.isEmpty(), title, isNeedParent, nodePosition)
    }

    /**
     * Какая-то нода была выбрана
     * */
    fun nodeClicked(nodeIndex: Long) = apply {
        this.parentNode = nodeIndex
        this.uiState.value =
            MainScreenState.NewNode(
                nodeList,
                nodeList.isEmpty(),
                nodeTitle,
                parentNode,
                nodePosition
            )
    }

    /**
     * Точка [LazyTreeLayout] была кликнута
     * */
    fun coordinateClicked(point: Point) = apply {
        this.nodePosition = point
        val node = ItemTreeNode.TreeNodeData(
            nodeTitle!!, -1,
            ItemTreeNode.TreeNodePreferences(
                nodePosition!!.x, nodePosition!!.y, NodeViewProperty.DEFAULT_HEIGHT,
                NodeViewProperty.DEFAULT_WIDTH
            ), false
        )

        val data = nodeList.toMutableList()
        data.add(node)
        this.uiState.value =
            MainScreenState.NewNode(data, nodeList.isEmpty(), nodeTitle, parentNode, nodePosition)


        //Last state. Need user confirm

    }

    /**
     * Завершаем создание ноды. Проверяем наличие всех данных. Создаём запись в бд
     * */
    fun finishConstructor(): ItemTreeNode.TreeNodeData {

        //TODO create in database
        return ItemTreeNode.TreeNodeData(
            nodeTitle!!,
            nodeIndex = 0,
            preferences = ItemTreeNode.TreeNodePreferences(
                nodePosition!!.x,
                nodePosition!!.y,
                200,
                200
            )
        )
    }

    fun clickNavigationBottomButton() {

        if (nodeTitle == null) {
            navigationChannel.trySend(
                Screen.CreateNode.navigationWithArgs(Unit)
            )
            return
        }
        //Подтверждение создания ноды. Заводим запись в бд и передаём контроль обратно ViewModel
        viewModelScope.launch {
            treeManager.insertNode(
                LibNode(libName, nodeTitle!!, parentNode),
                NodePosition(-1L, nodePosition!!.x, nodePosition!!.y),
                NodeViewProperty(-1L)
            )
            returnController.returnControl()
        }
    }
}