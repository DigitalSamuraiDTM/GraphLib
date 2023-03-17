package com.digitalsamurai.graphlib.logic.mediator

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Посредник позволяет передать информацию через Screen's
 *
 *
 * Я мог передавать результат между Screen's С помощью
 * [currentBackStackEntry], но как по мне это не так профитно, передавать данные через Ui, когда можно передавать напрямую через ViewModel с посредником
 * */
class NodeInfoMediator : NodeInfoMediatorGetter {

    var title: String? = null
        set(value) {
            value?.let { listener?.onUpdate(value) }
            field = value
        }

    private var listener : NodeInfoMediatorGetter.UpdateTitleListener? = null
    override fun setUpdateTitleListener(listener: NodeInfoMediatorGetter.UpdateTitleListener) {
        this.listener = listener
    }

    override fun getTitle(remove: Boolean): String? {
        val copy = title
        if (remove) title = null
        return copy
    }
}