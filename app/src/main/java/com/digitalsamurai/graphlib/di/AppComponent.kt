package com.digitalsamurai.graphlib.di

import com.digitalsamurai.di.GraphComponent
import com.digitalsamurai.di.GraphModule
import com.digitalsamurai.graphlib.database.di.DatabaseModule
import com.digitalsamurai.graphlib.di.general.MainComponent
import com.digitalsamurai.graphlib.ui.createlib.LibCreatingDialog
import com.digitalsamurai.graphlib.ui.libs.LibsFragment
import com.digitalsamurai.graphlib.ui.libs.LibsViewModel
import com.digitalsamurai.graphlib.ui.start.vm.StartScreenViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class])
interface AppComponent {

    //subcomponent
    fun mainComponent() : MainComponent

}