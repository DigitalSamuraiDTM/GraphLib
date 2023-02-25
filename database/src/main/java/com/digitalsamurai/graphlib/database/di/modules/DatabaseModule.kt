package com.digitalsamurai.graphlib.database.di.modules

import android.content.Context
import androidx.room.Room
import com.digitalsamurai.graphlib.database.preferences.GraphPreferences
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.database.tree.TreeManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module()
class DatabaseModule {


    @Provides
    fun provideDatabase(applicationContext : Context) : GraphDatabase = Room.databaseBuilder(applicationContext,GraphDatabase::class.java,"graph_database").build()

    @Provides
    fun providePreferences(context : Context) : GraphPreferences{
        return GraphPreferences(context)
    }

    @Provides
    fun provideTreeManagerFactory(graphDatabase: GraphDatabase) : TreeManager.Factory = TreeManager.Factory(graphDatabase)
}