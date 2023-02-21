package com.digitalsamurai.di


import com.digitalsamurai.view.TreeView
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@GraphScope
@Subcomponent(modules = [GraphModule::class,])
interface GraphComponent{




    fun injectTreeView(treeView: TreeView)
}