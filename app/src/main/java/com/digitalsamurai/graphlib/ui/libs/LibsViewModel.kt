package com.digitalsamurai.graphlib.ui.libs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalsamurai.graphlib.database.room.libs.Lib
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class LibsScreenViewModel : ViewModel() {



   private val _libsState = MutableStateFlow<List<Lib>>(emptyList())
    val libsState = _libsState.asStateFlow()

    init {

    }
    fun initLibsViewModel(){

    }

    init {
        viewModelScope.launch {

        }
    }

    private var selectedLibJob : Job? = null

    fun insertLib(name : String){
        //TODO NOT UPDATED AFTER CREATING FROM DIALOG FRAGMENT
//        CoroutineScope(Dispatchers.Main).launch {
//            database.libDao().insertLib(Lib(name, LocalDateTime.now()))
//        }
    }

    fun getAll(){

    }
}