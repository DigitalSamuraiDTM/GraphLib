package com.digitalsamurai.graphlib.di.general

import com.digitalsamurai.graphlib.ui.main.vm.MainViewModel
import dagger.Subcomponent


@MainScope
@Subcomponent(modules = [MainModule::class])
interface MainComponent {


    fun injectMainViewModel(mainViewModel: MainViewModel)


}