package com.digitalsamurai.graphlib.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.databinding.FragmentMainBinding
import javax.inject.Inject

class MainFragment : Fragment() {

    private lateinit var binding : FragmentMainBinding

    @Inject
    lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater,container,false)


        GraphLibApp.startComponent.injectMainFragment(this)


        val libName = arguments?.getString(BUNDLE_LIB_NAME)
        if(libName!=null){
            viewModel.initData(libName)
        }

        return binding.root
    }

    companion object{
        const val BUNDLE_LIB_NAME = "BundleLibName"
    }

}