package com.digitalsamurai.graphlib.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.database.room.libs.Lib
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

    lateinit var treeManager: TreeManager

    lateinit var db : GraphDatabase

    val libName = "test"
    @Before
    fun before(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GraphDatabase::class.java).build()
        treeManager = TreeManager.Factory(db).build(libName)
    }

    @Test
    fun complexTest() = runBlocking {
        //before init some data with 5 nodes

        val libDao = db.libDao()
        val libNodeDao = db.libNodeDao()

        val lib = Lib(libName, LocalDateTime.now())
        libDao.insertLib(lib)

        val prefInitTree = treeManager.initTree()

        assert(prefInitTree==null)

        val rootIndex = libNodeDao.insert(LibNode(libName,"Main",null, ChildNodes(),0))
        val test1Index = libNodeDao.insert(LibNode(libName,"Test1",rootIndex,ChildNodes(),0))
        val test2Index = libNodeDao.insert(LibNode(libName,"Test2",rootIndex,ChildNodes(),0))
        val test11Index = libNodeDao.insert(LibNode(libName,"Test11",test1Index,ChildNodes(),0))
        val test21Index = libNodeDao.insert(LibNode(libName,"Test21",test2Index,ChildNodes(),0))
        val test12Index = libNodeDao.insert(LibNode(libName,"Test12",test1Index,ChildNodes(),0))
        val test111Index = libNodeDao.insert(LibNode(libName,"Test12",test11Index,ChildNodes(),0))

        val initTree = treeManager.initTree()

        assert(initTree!=null)

        assert(initTree?.childs?.size!=0)

        val result = db.libDao().deleteLib(lib)

        //init tree and check all
    }

    @After
    fun after(){


        db.close()
    }
}