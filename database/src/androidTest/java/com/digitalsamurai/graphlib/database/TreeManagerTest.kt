package com.digitalsamurai.graphlib.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.database.tree.TreeManager
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TreeManagerTest {

    lateinit var treeManager: TreeManager

    lateinit var db : GraphDatabase
    @Before
    fun before(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GraphDatabase::class.java).build()
        treeManager = TreeManager.Factory(db).build("test")
    }

    @Test
    fun complexTest() = runBlocking {
        //before init some data with 5 nodes



        //init tree and check all
    }

    @After
    fun after(){
        db.close()
    }
}