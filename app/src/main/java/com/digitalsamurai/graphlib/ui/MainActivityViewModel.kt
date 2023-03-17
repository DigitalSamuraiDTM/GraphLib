package com.digitalsamurai.graphlib.ui

import androidx.lifecycle.ViewModel
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.preferences.GraphPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel : ViewModel() {

    @Inject
    lateinit var pref : GraphPreferences



    init {

        GraphLibApp.appComponent.injectMainActivityViewModel(this)
    }
    val lastGraphName : String?
        get() = pref.lastOpenedGraph
}