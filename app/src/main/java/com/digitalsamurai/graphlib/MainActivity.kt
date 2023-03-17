package com.digitalsamurai.graphlib

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.digitalsamurai.graphlib.ui.MainActivityViewModel
import com.digitalsamurai.graphlib.ui.navigation.Navigation

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //до установки контента необходимо понять что будем показывать в первую очередь
        // для этого создаем вью модель, которая предоставляет инфу из preferences

        setContent {
            Navigation(viewModel.lastGraphName)
        }
    }
}
