package com.digitalsamurai.graphlib.database.tree

import com.digitalsamurai.graphlib.database.di.DaggerDatabaseComponent
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.database.room.nodes.NodePresentation
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode

class TreeManager private constructor(val libraryName: String, val database: GraphDatabase) {

    init {


    }

    /**
     * Init Library tree with recursion. Need some time with suspend
     * @return RootNode with child nodes if exist
     * */
    suspend fun initTree(): NodePresentation? {
        val root = database.libNodeDao().getRootNodeByLib(libraryName)
        return if (root != null) {
            val presentation = initNodePresentation(root)
            presentation
        } else {
            null
        }
    }

    /**
     * Childs with recursion. May be use a lot of memory
     * @param [listIndex] - indexes of childs (may be empty)
     * @return childs presentation
     * */
    private suspend fun initChildsRecursively(listIndex: List<Long>): List<NodePresentation> {
        var out: MutableList<NodePresentation> = ArrayList()
        listIndex.forEach {
            val libNode = database.libNodeDao().getNodeByIndex(it)
            if (libNode != null) {
                out.add(initNodePresentation(libNode))
            } else {
                //log info
            }
        }
        return out
    }

    /**
     * Init node presentation and all childs
     * */
    private suspend fun initNodePresentation(libNode: LibNode): NodePresentation {
        val rootPosition = database.nodePosition().getNodeByIndex(libNode.nodeIndex)
        val rootUiProperty = database.nodeViewProperty().getByIndex(libNode.nodeIndex)

        return NodePresentation.Builder(libNode)
            .setNodePosition(rootPosition)
            .setNodeViewProperty(rootUiProperty)
            .build()
    }


    class Factory internal constructor(private val graphDatabase: GraphDatabase) {

        fun build(libraryName: String): TreeManager {
            return TreeManager(libraryName, graphDatabase)
        }
    }


}