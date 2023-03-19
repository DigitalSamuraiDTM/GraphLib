package com.digitalsamurai.graphlib.database.tree

import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.database.room.nodes.Node
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode

class TreeManager private constructor(val libraryName: String, val database: GraphDatabase) {

    init {


    }
    private var rootNode : Node? = null

    /**
     * Init Library tree with recursion. Need some time with suspend
     * @return RootNode with child nodes if exist
     * */
    private suspend fun initTree() : Node?{
        val dbNode = database.libNodeDao().getRootNodeByLib(libraryName)
        return if (dbNode != null) {
            rootNode = initNodePresentation(dbNode,null)
            rootNode
        } else {
            null
        }
    }

    /**
     * Check every element in tree O(n)
     * todo speed can increased with indexed tree but not critical
     * */
    fun findNodeByIndex(nodeIndex : Long) : Node? {
        rootNode?.let {
            val c = findChildNodeByIndex(it,nodeIndex)
            if (c!=null){
                return c
            }
        }
        return null
    }

    /**
     * @param [parent] root of tree or subtree
     * @param [index] index node to find
     *
     * @return child with index
     *
     * */
    private fun findChildNodeByIndex(parent : Node, index : Long) : Node?{
        return if (parent.nodeInfo.nodeIndex==index){
            parent
        } else{
            parent.childs.forEach {
                findChildNodeByIndex(it,index)?.let {
                    return it
                }
            }
            null
        }
    }


    suspend fun getRootNode(): Node?  = rootNode





    /**
     * Init node presentation and all childs
     * Create node and after call [Node.updateParentNode] for setup true parent
     * It is cyclic dependency with every node and their childs
     * */
    private suspend fun initNodePresentation(libNode: LibNode, parentNode : Node?): Node {
        val rootPosition = database.nodePosition().getNodeByIndex(libNode.nodeIndex)
        val rootUiProperty = database.nodeViewProperty().getByIndex(libNode.nodeIndex)
        var childsList = mutableListOf<Node>()
        libNode.childs.childs.forEach {
            val dbNodeInfo = database.libNodeDao().getNodeByIndex(it)
            if (dbNodeInfo!=null){
                childsList.add(initNodePresentation(dbNodeInfo, null))
            }
        }
        //create node
        val node = Node.Builder(libNode)
            .setNodePosition(rootPosition)
            .setNodeViewProperty(rootUiProperty)
            .setParentNode(parentNode)
            .setChilds(childsList)
            .build()
        //node was created -> we can set parent node
        node.childs.forEach { it.updateParentNode(node) }
        return node
    }





    class Factory internal constructor(private val graphDatabase: GraphDatabase) {

        suspend fun build(libraryName: String): TreeManager {
            val manager = TreeManager(libraryName, graphDatabase)
            manager.initTree()
            return manager
        }
    }


}