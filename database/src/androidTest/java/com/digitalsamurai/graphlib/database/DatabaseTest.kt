package com.digitalsamurai.graphlib.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.database.room.libs.Lib
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode
import com.digitalsamurai.graphlib.database.room.nodes.node.entity.ChildNodes
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.time.LocalDateTime

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var db : GraphDatabase

    @Before
    fun before(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,GraphDatabase::class.java).build()
    }
    @Test
    fun testInsertAndDeleteLib() = runBlocking {
        val lib = Lib("TestLib",LocalDateTime.now())
        db.libDao().insertLib(lib)
        val count = db.libDao().countLibs()
        assert(count==1)
        db.libDao().deleteLib(lib)
        assert(db.libDao().countLibs()==0)


    }

    @Test
    fun testLibNodeDao() = runBlocking {
        val libNodeDao = db.libNodeDao()
        val lib = db.libDao()

        val dbName = "test"
        lib.insertLib(Lib(dbName, LocalDateTime.now()))
        val parentIndex = libNodeDao.insert(LibNode(dbName,"TITLE",null, ChildNodes(ArrayList()),0))

        val childIndex = libNodeDao.insert(LibNode(dbName,"CHILD",parentIndex, ChildNodes(ArrayList()),0))
        val childChildIndex = libNodeDao.insert(LibNode(dbName,"CHILD",childIndex, ChildNodes(ArrayList()),0))
        delay(5000L)
        println(parentIndex)
        println(childIndex)
        println(childChildIndex)
    }

    @Test
    fun testEmptyRootNode() = runBlocking {
        val libNodeDao = db.libNodeDao()

        val root = libNodeDao.getRootNodeByLib("testTableNotExist")
        assert(root==null)
    }

    @After
    fun after(){
        db.close()
    }
}