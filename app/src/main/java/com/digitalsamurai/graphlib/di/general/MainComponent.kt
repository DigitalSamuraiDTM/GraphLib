package com.digitalsamurai.graphlib.di.general

import com.digitalsamurai.graphlib.ui.main.MainFragment
import dagger.Subcomponent


@MainScope
@Subcomponent(modules = [MainModule::class])
interface MainComponent {


    fun injectMainFragment(fragment : MainFragment)
}