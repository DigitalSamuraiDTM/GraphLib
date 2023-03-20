package com.digitalsamurai.graphlib.database.room.nodes.node.entity

enum class NodeDeleteStrategy {
    /**
     * При удалении записи все дочерние элементы будут связаны с родителем удаляемой записи
     * */
    CONNECT_CHILDREN_TO_PARENT,


}