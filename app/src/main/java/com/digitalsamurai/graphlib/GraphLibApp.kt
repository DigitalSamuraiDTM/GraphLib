package com.digitalsamurai.graphlib

import android.app.Application
import android.content.Context
import com.digitalsamurai.graphlib.di.AppComponent
import com.digitalsamurai.graphlib.di.AppModule
import com.digitalsamurai.graphlib.di.DaggerAppComponent
import com.digitalsamurai.graphlib.di.general.StartComponent

class GraphLibApp: Application() {

    override fun onCreate() {
        Companion.applicationContext = this.applicationContext

        super.onCreate()
    }

    companion object{
        lateinit var applicationContext : Context


        val appComponent : AppComponent
        get() {
            return DaggerAppComponent.builder().appModule(AppModule(this.applicationContext)).build()
        }
        val startComponent : StartComponent
        get() {
            return appComponent.mainComponent()
        }


    }
}