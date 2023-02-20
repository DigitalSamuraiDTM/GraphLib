package com.digitalsamurai.graphlib.ui.createlib.state

sealed class EnteredNameState(val enteredName : String) {
    class NameOk(val name : String) : EnteredNameState(name)
    class NameExist(val name : String ) : EnteredNameState(name)
    class NameNothing(val name : String) : EnteredNameState(name)
}