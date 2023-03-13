package com.digitalsamurai.graphlib.ui.customscreen.tree_layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.runtime.*
import com.digitalsamurai.graphlib.ui.customscreen.tree_layout.node.ItemTreeNode

@Composable
fun rememberTreeLayoutItemProvider(scope: TreeLayoutScope.() -> Unit): TreeLayoutItemProvider {
    val lazyTreeLayoutScopeState = remember { mutableStateOf(scope) }.apply {
        value = scope
    }
    return remember {
        TreeLayoutItemProvider(derivedStateOf {
            val layoutScope = TreeLayoutScopeImpl().apply(lazyTreeLayoutScopeState.value)
            layoutScope.items
        })
    }


}

/**
 * [TreeLayoutItemProvider] обеспечивает понимание того, какие элементы стоит отображать на экране, а какие нет (lazy)
 * Фактически, можно сказать, что в какой-то степени работает как [RecyclerAdapter]
 * */


@OptIn(ExperimentalFoundationApi::class)
class TreeLayoutItemProvider(private val itemState: State<List<ItemTreeNode>>) :
    LazyLayoutItemProvider {
    override val itemCount: Int
        get() = itemState.value.size


    /**
     * Функция отображает текущий элемент
     * */
    @Composable
    override fun Item(index: Int) {
        val item = itemState.value.getOrNull(index)
        item?.content?.invoke(item.data)
    }

    /**
     * @param boundaries текущий фокус экрана
     * @return список элементов, которые необходимо отобразить
     * */
    fun getItemInRange(boundaries: ViewBoundaries): List<Int> {
        val result = mutableListOf<Int>()

        itemState.value.forEachIndexed { index, treeNode ->
            val listItem = treeNode.data
            if (listItem.preferences.x in boundaries.fromX..boundaries.toX &&
                listItem.preferences.y in boundaries.fromY..boundaries.toY
            ) {
                result.add(index)
            }
        }
        return result
    }

    /**
     * Получить конкретный элемент из списка
     * */
    fun getItem(index: Int): ItemTreeNode? {
        return itemState.value.getOrNull(index)
    }


}