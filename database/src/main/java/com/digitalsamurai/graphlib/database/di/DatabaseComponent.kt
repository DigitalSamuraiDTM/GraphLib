package com.digitalsamurai.graphlib.database.di

import androidx.room.Database
import dagger.Subcomponent

@DatabaseScope
@Subcomponent(modules = [DatabaseModule::class])
interface DatabaseComponent {

}