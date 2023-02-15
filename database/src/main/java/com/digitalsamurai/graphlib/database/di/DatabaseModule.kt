package com.digitalsamurai.graphlib.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.digitalsamurai.graphlib.database.preferences.GraphPreferences
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(applicationContext : Context) : GraphDatabase = Room.databaseBuilder(applicationContext,GraphDatabase::class.java,"graph_database").build()

    @Provides
    @Singleton
    fun providePreferences(context : Context) : GraphPreferences{
        return GraphPreferences(context)
    }
}