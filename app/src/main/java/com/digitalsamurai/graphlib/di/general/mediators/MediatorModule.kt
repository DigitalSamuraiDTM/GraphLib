package com.digitalsamurai.graphlib.di.general.mediators

import com.digitalsamurai.graphlib.di.general.MainScope
import com.digitalsamurai.graphlib.logic.mediator.NodeInfoMediator
import com.digitalsamurai.graphlib.logic.mediator.NodeInfoMediatorGetter
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
class MediatorModule {


    @Provides
    @MainScope
    fun provideNodeInfoMediator() : NodeInfoMediator{
        return NodeInfoMediator()
    }

    @Provides
    @MainScope
    fun provideNodeInfoMediatorGetter(mediator: NodeInfoMediator) : NodeInfoMediatorGetter{
        return mediator
    }
}