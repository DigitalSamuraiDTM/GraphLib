package com.digitalsamurai.graphlib.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.database.room.libs.Lib
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode
import com.digitalsamurai.graphlib.database.room.nodes.node.entity.ChildNodes
import com.digitalsamurai.graphlib.database.room.nodes.node.entity.NodeDeleteStrategy
import com.digitalsamurai.graphlib.database.room.nodes.position.NodePosition
import com.digitalsamurai.graphlib.database.room.nodes.properties.NodeViewProperty
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var db: GraphDatabase

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GraphDatabase::class.java).build()
    }

    /**
     * Создание и удаление таблицы
     * */
    @Test
    fun testInsertAndDeleteLib() = runBlocking {
        val lib = Lib("TestLib", LocalDateTime.now())
        db.libDao().insertLib(lib)
        val count = db.libDao().countLibs()
        assert(count == 1)
        db.libDao().deleteLib(lib)
        assert(db.libDao().countLibs() == 0)


    }

    @Test
    fun testLibNodeDao() = runBlocking {
        val libNodeDao = db.libNodeDao()
        val lib = db.libDao()

        val dbName = "test"
        lib.insertLib(Lib(dbName, LocalDateTime.now()))
        val parentIndex =
            libNodeDao.insert(LibNode(dbName, "TITLE", null, ChildNodes(ArrayList()), 0))

        val childIndex = libNodeDao.insert(
            LibNode(
                dbName,
                "CHILD",
                parentIndex.nodeIndex,
                ChildNodes(ArrayList()),
                0
            )
        )
        val childChildIndex = libNodeDao.insert(
            LibNode(
                dbName,
                "CHILD",
                childIndex.nodeIndex,
                ChildNodes(ArrayList()),
                0
            )
        )
        delay(5000L)
        println(parentIndex)
        println(childIndex)
        println(childChildIndex)
    }

    @Test
    fun testEmptyRootNode() = runBlocking {
        val libNodeDao = db.libNodeDao()

        val testLib = Lib("test", LocalDateTime.now())

        var root = libNodeDao.getRootNodeByLib("test")
        assert(root == null)

        val libDao = db.libDao()
        libDao.insertLib(testLib)
        val index = libNodeDao.insert(LibNode("test", "Main", null, ChildNodes(), 0))

        root = libNodeDao.getRootNodeByLib("test")
        assert(root != null)

        libDao.deleteLib(testLib)

        assert(true)

    }

    /**
     * При удалении записи [LibNode] должны удаляться связанные ключом записи [NodePosition],[NodeViewProperty]
     * */
    @Test
    fun testNodeInfoForeignKeys() = runBlocking {
        db.libDao().insertLib(Lib("test", LocalDateTime.now()))
        val libRoot = LibNode("test", "Root", null, ChildNodes(), 0)


        val step1 = db.libNodeDao().insert(libRoot)
        val nodeView = NodeViewProperty(step1.nodeIndex, 200, 322)
        val nodePosition = NodePosition(step1.nodeIndex, 0, 0)
        val step2 = db.nodePosition().insertNodePosition(nodePosition)
        val step3 = db.nodeViewProperty().insert(nodeView)


        assert(db.nodePosition().getNodeByIndex(step1.nodeIndex) != null)
        assert(db.nodeViewProperty().getByIndex(step1.nodeIndex) != null)
        val deleteRoot =
            db.libNodeDao().delete(NodeDeleteStrategy.CONNECT_CHILDREN_TO_PARENT, step1)
        val allPositions = db.nodePosition().getAll()
        val allViewProperty = db.nodeViewProperty().getAll()
        val root = db.libNodeDao().getNodeByIndex(step1.nodeIndex)
        assertNull(root)
        assert(db.nodePosition().getNodeByIndex(step1.nodeIndex) == null)
        assert(db.nodeViewProperty().getByIndex(step1.nodeIndex) == null)


    }

    /**
     * При удалении записи [LibNode] при стратегии [NodeDeleteStrategy.CONNECT_CHILDREN_TO_PARENT] дочки из колонки [LibNode.COLUMN_CHILDS] должны привязаться к родителю [LibNode.COLUMN_PARENT_INDEX]
     * */
    @Test
    fun testChildConstraints() = runBlocking {
        db.libDao().insertLib(Lib("test", LocalDateTime.now()))

        // Создаем рут и связываем последовательно две записи. Получаем связь Root -> child1 -> child2
        val libRoot = LibNode("test", "Root", null, ChildNodes(), 0)
        val libResult = db.libNodeDao().insert(libRoot)
        var child1 =
            db.libNodeDao().insert(LibNode("test", "child1", libRoot.nodeIndex, ChildNodes()))
        val child2 =
            db.libNodeDao().insert(LibNode("test", "child2", child1.nodeIndex, ChildNodes()))

        //Проверяем, что наши связи были правильно построены
        assert(db.libNodeDao().getNodeByIndex(child1.nodeIndex)?.childs?.childs?.size == 1)
        assert(db.libNodeDao().getRootNodeByLib("test")?.childs?.childs?.size == 1)

        //Удаляем запись child2. Остается цепочка Root -> child1
        db.libNodeDao().delete(NodeDeleteStrategy.CONNECT_CHILDREN_TO_PARENT, child2)
        //Проверяем, что child1 забыла про связь с удаленной child2
        assert(db.libNodeDao().getNodeByIndex(child1.nodeIndex)?.childs?.childs?.size == 0)

        //Добавляем связь child3. Текущая цепочка: Root -> child1 -> child3
        val child3 =
            db.libNodeDao().insert(LibNode("test", "child3", child1.nodeIndex, ChildNodes()))
        child1 = db.libNodeDao().getNodeByIndex(child1.nodeIndex)!!

        //Удаляем child1 по стратегии связи с родителями. Используя эту стратегию мы получаем цепочку Root -> child3 (дочка child1 была прицеплена к родителю child1)
        db.libNodeDao().delete(NodeDeleteStrategy.CONNECT_CHILDREN_TO_PARENT, child1)

        //Проверяем, что связи построены правильно и цепочка соответствует ожиданиям
        val updatedRoot = db.libNodeDao().getRootNodeByLib("test")
        assert(updatedRoot!!.childs.childs.size == 1)
        assert(updatedRoot.childs.childs.get(0) == child3.nodeIndex)
    }

    @After
    fun after() {
        db.close()
    }
}