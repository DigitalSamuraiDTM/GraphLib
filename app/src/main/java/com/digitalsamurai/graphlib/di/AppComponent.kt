package com.digitalsamurai.graphlib.di

import com.digitalsamurai.graphlib.database.di.modules.DatabaseModule
import com.digitalsamurai.graphlib.di.general.MainComponent
import com.digitalsamurai.graphlib.di.general.MainModule
import com.digitalsamurai.graphlib.ui.MainActivityViewModel
import com.digitalsamurai.graphlib.ui.createlib.vm.CreateLibViewModel
import com.digitalsamurai.graphlib.ui.libs.LibsScreenViewModel
import com.digitalsamurai.graphlib.ui.start.vm.StartScreenViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class])
interface AppComponent {

    fun injectMainActivityViewModel(viewModel : MainActivityViewModel)

    //subcomponent
    fun mainComponent(mainModule: MainModule) : MainComponent

    fun injectStartViewModel(viewModel : StartScreenViewModel)

    fun injectCreateLibViewModel(viewModel : CreateLibViewModel)

    fun injectLibsViewModel(libsScreenViewModel: LibsScreenViewModel)
}