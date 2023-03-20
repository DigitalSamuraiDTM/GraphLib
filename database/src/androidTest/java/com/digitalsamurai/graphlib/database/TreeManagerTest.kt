package com.digitalsamurai.graphlib.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.database.room.libs.Lib
import com.digitalsamurai.graphlib.database.room.nodes.Node
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode
import com.digitalsamurai.graphlib.database.room.nodes.node.entity.ChildNodes
import com.digitalsamurai.graphlib.database.tree.TreeManager
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class TreeManagerTest {

    lateinit var factory: TreeManager.Factory

    lateinit var db : GraphDatabase

    val libName = "test"
    @Before
    fun before(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GraphDatabase::class.java).build()
        factory = TreeManager.Factory(db)
    }

    @Test
    fun complexTest() = runBlocking {
        //before init some data with 5 nodes


        val libDao = db.libDao()
        val libNodeDao = db.libNodeDao()

        val lib = Lib(libName, LocalDateTime.now())
        libDao.insertLib(lib)


        val rootIndex = libNodeDao.insert(LibNode(libName,"Main",null, ChildNodes(),0))
        val test1Index = libNodeDao.insert(LibNode(libName,"Test1",rootIndex.nodeIndex,ChildNodes(),0))
        val test2Index = libNodeDao.insert(LibNode(libName,"Test2",rootIndex.nodeIndex,ChildNodes(),0))
        val test11Index = libNodeDao.insert(LibNode(libName,"Test11",test1Index.nodeIndex,ChildNodes(),0))
        val test21Index = libNodeDao.insert(LibNode(libName,"Test21",test2Index.nodeIndex,ChildNodes(),0))
        val test12Index = libNodeDao.insert(LibNode(libName,"Test12",test1Index.nodeIndex,ChildNodes(),0))
        val test111Index = libNodeDao.insert(LibNode(libName,"Test12",test11Index.nodeIndex,ChildNodes(),0))


        //test init
        val treeManager = factory.build(libName)
        val root = treeManager.getRootNode()

        assert(root!=null)

        assert(root?.childs?.size!=0)

        showAllTreeInConsole(root!!)

        //test checking
        var foundedChild = treeManager.findNodeByIndex(test111Index.nodeIndex)
        println(foundedChild?.nodeInfo?.title)
        assert(foundedChild!=null)

        foundedChild = treeManager.findNodeByIndex(-1)
        assert(foundedChild==null)


        //When change title variable a -> it is change data in tree
        val a = root.childs.get(0)
        a.nodeInfo.title = "CHANGED"
        println(a.nodeInfo.nodeIndex)
        val founded = treeManager.findNodeByIndex(a.nodeInfo.nodeIndex)
        println(founded?.nodeInfo?.title)

        val result = db.libDao().deleteLib(lib)


//        root.childs.get(0).parentNode?.childs?.get(0)?.parentNode?.childs?.get(0)?.parentNode?.childs?.get(0)?.parentNode?.childs?.get(0)?.parentNode?.childs?.get(0)?.parentNode?.childs?.get(0)?.parentNode?.childs?.get(0)


        assert(db.libNodeDao().getRootNodeByLib(libName)==null)


        //init tree and check all
    }

    private fun showAllTreeInConsole(parent : Node){
        println(parent.nodeInfo.nodeIndex)
        parent.childs.forEach {
            println("go deep to ${it.nodeInfo.nodeIndex}. His parent: ${it.parentNode?.nodeInfo?.nodeIndex}")
            showAllTreeInConsole(it)
            println("emerge to ${parent.nodeInfo.nodeIndex}. His parent: ${parent.parentNode?.nodeInfo?.nodeIndex}")
        }
    }

    @After
    fun after(){


        db.close()
    }
}