package com.digitalsamurai.graphlib.di.general

import com.digitalsamurai.di.GraphComponent
import com.digitalsamurai.di.GraphModule
import com.digitalsamurai.graphlib.ui.main.MainFragment
import com.digitalsamurai.graphlib.ui.start.vm.StartScreenViewModel
import dagger.Subcomponent


@MainScope
@Subcomponent(modules = [MainModule::class])
interface MainComponent {


    //subcomponents of application hierarchy
    fun graphComponent(graphModule : GraphModule) : GraphComponent

    fun injectStartViewModel(viewModel : StartScreenViewModel)

    fun injectMainFragment(arg0 : MainFragment)


}