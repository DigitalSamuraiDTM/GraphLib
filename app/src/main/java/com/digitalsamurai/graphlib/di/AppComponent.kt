package com.digitalsamurai.graphlib.di

import com.digitalsamurai.graphlib.database.di.DatabaseModule
import com.digitalsamurai.graphlib.di.general.StartComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class])
interface AppComponent {

    //subcomponent
    fun mainComponent() : StartComponent

}