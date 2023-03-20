package com.digitalsamurai.graphlib.logic.createnode

import android.graphics.Point
import android.graphics.PointF
import androidx.compose.runtime.MutableState
import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode
import com.digitalsamurai.graphlib.ui.main.MainScreenState

/**
 * [NodeBuilder] делегирует на себя задачи создания новой записи (БЕЗ учёта начинки)
 * [MainViewModel] трекает кнопки нажатия создания новой записи и передаёт управление
 * Во время создания записи интерфейс имеет состояние [MainScreenState.NewNode]
 * Можно, конечно, довести этот класс до ООП паттерна "Цепочка обязанностей", но это слишком много кода, который, на данный момент, не даст никаких преимуществ
 * поэтому оставляю полукастомный шаблон "Конструктор" :)
 * */
class NodeBuilder {


    var uiState: MutableState<MainScreenState.NewNode>? = null

    private var nodePosition: Point = Point(0, 0)

    private var parentNode: Long = -1

    private var nodeTitle: String = "Example of title"

    private var nodeList: List<ItemTreeNode.TreeNodeData> = emptyList()

    /**
     * Начинаем создание новой ноды
     * @param uiState - передаём управление состояниями в NodeConstructor
     * @param nodeList - текущее состояние узлов на экране. Также необходимо для понимания того создаём мы Root ноду или нет
     * Он будет управлять состоянием ui до завершения создания Ноды
     * */
    fun startConstructor(
        nodeList: List<ItemTreeNode.TreeNodeData>,
        uiState: MutableState<MainScreenState.NewNode>
    ) = apply {
        this.uiState = uiState
        this.nodeList = nodeList
        this.uiState!!.value =
            MainScreenState.NewNode(nodeList, nodeTitle, parentNode, nodePosition)
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
        this.uiState!!.value =
            MainScreenState.NewNode(nodeList, title, isNeedParent, nodePosition)
    }

    /**
     * Какая-то нода была выбрана
     * */
    fun nodeClicked(nodeIndex: Long) = apply {
        this.parentNode = nodeIndex
        this.uiState!!.value =
            MainScreenState.NewNode(nodeList, nodeTitle, parentNode, nodePosition)
    }

    /**
     * Точка [LazyTreeLayout] была кликнута
     * */
    fun coordinateClicked(point: Point) = apply {
        this.nodePosition = point
        this.uiState!!.value =
            MainScreenState.NewNode(nodeList, nodeTitle, parentNode, nodePosition)

    }

    /**
     * Завершаем создание ноды. Проверяем наличие всех данных. Создаём запись в бд
     * */
    fun finishConstructor(): ItemTreeNode.TreeNodeData {

        //TODO create in database
        return ItemTreeNode.TreeNodeData(
            nodeTitle,
            nodeIndex = 0,
            preferences = ItemTreeNode.TreeNodePreferences(nodePosition.x, nodePosition.y, 200, 200)
        )
    }
}