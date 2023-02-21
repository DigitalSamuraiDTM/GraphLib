package com.digitalsamurai.graphlib

import android.app.Application
import android.content.Context
import com.digitalsamurai.graphlib.di.AppComponent
import com.digitalsamurai.graphlib.di.AppModule
import com.digitalsamurai.graphlib.di.DaggerAppComponent
import com.digitalsamurai.graphlib.di.general.MainComponent
import com.digitalsamurai.graphlib.di.general.MainModule

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
        val startComponent : MainComponent
        get() {
            return appComponent.mainComponent(MainModule())
        }
        val mainComponent : MainComponent
        get() {
            return appComponent.mainComponent(MainModule())
        }

    }
}