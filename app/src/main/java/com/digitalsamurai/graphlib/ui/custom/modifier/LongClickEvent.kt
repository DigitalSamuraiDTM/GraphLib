package com.digitalsamurai.graphlib.ui.custom.modifier

data class LongClickEvent(
    val clickTimeMillis : Long = 1000L,
    val onLongClick : ()->Unit)