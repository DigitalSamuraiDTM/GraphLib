package com.digitalsamurai.graphlib.ui.main

import androidx.lifecycle.ViewModel
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import javax.inject.Inject

class MainViewModel @Inject constructor(private val database : GraphDatabase) : ViewModel() {



    fun initData(libName : String){

    }
}