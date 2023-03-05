package com.digitalsamurai.graphlib.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CyclicTest {

    @Test
    fun cyclickTest(){
        val a1 = A("a1")
        val a2 = A("a2")
        a1.a = a2
        a2.a = a1

        print(a2.a?.a?.a?.a?.a?.a?.a?.a?.a?.a?.name)

        assert(true)
    }

    class A(val name : String){

        var a : A? = null
    }
}