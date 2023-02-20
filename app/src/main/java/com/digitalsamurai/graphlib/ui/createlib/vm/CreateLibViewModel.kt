package com.digitalsamurai.graphlib.ui.createlib.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.database.room.libs.Lib
import com.digitalsamurai.graphlib.ui.createlib.state.EnteredNameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CreateLibViewModel : ViewModel() {


    @Inject
    lateinit var database : GraphDatabase


    private var _enteredName = mutableStateOf<EnteredNameState>(EnteredNameState.NameNothing(""))
    val enteredName : State<EnteredNameState>
    get() = _enteredName

    private var enteredText : String = ""



    init {

        GraphLibApp.startComponent.injectCreateLibViewModel(this)
    }

    //only for lib name text field

    private var inputTextJob : Job? = null

    fun inputTextEvent(text : String){
        enteredText = text
        inputTextJob?.cancel()
        if (text==""){
           _enteredName.value = EnteredNameState.NameNothing(text)
            return
        }

        inputTextJob = viewModelScope.launch {
            val liba = database.libDao().findLibByName(text)
            when (liba.size) {
                1 -> { _enteredName.value = EnteredNameState.NameExist(text) }
                0 -> { _enteredName.value = EnteredNameState.NameOk(text) }
            }
        }
    }

    private val _createLibFlow = MutableSharedFlow<String?>()
    val createLibFlow : SharedFlow<String?>
    get() = _createLibFlow

    fun inputClickEvent() {
        viewModelScope.launch {
            val liba = database.libDao().findLibByName(enteredText)
            if (liba.isEmpty()){
                database.libDao().insertLib(Lib(enteredText, LocalDateTime.now()))
                _createLibFlow.emit(enteredText)
            } else{
                _createLibFlow.emit(null)
            }
        }
    }

}