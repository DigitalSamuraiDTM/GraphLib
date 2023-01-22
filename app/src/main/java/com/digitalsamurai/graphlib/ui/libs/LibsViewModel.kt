package com.digitalsamurai.graphlib.ui.libs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.database.room.libs.Lib
import com.digitalsamurai.graphlib.ui.createlib.LibItemViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class LibsViewModel @Inject constructor(private val database : GraphDatabase) : ViewModel(), LibItemViewModel {


    val libsFlow : Flow<List<Lib>>
    get() = database.libDao().observeAll().also {
        it.launchIn(viewModelScope)
        it.flowOn(Dispatchers.Main)
    }
    private val _selectedLibFlow = MutableSharedFlow<String>()
    val selectedLibFlow : Flow<String>
    get() = _selectedLibFlow

    init {

    }
    fun initLibsViewModel(){

    }


    private var selectedLibJob : Job? = null
    override fun selectLib(libName: String) {
        selectedLibJob?.cancel()
        selectedLibJob = viewModelScope.launch {
            _selectedLibFlow.emit(libName)
        }
    }

    fun insertLib(name : String){
        //TODO NOT UPDATED AFTER CREATING FROM DIALOG FRAGMENT
        CoroutineScope(Dispatchers.Main).launch {
            database.libDao().insertLib(Lib(name, LocalDateTime.now()))
        }
    }
}