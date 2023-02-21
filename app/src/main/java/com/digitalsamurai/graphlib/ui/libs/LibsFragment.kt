package com.digitalsamurai.graphlib.ui.libs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalsamurai.graphlib.databinding.FragmentLibsBinding

class LibsFragment : Fragment() {

    private lateinit var binding : FragmentLibsBinding




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLibsBinding.inflate(inflater,container,false)

        binding.libsRecyclerData.layoutManager = LinearLayoutManager(this.requireContext()).also {
            it.orientation = LinearLayoutManager.VERTICAL

        }





        return binding.root
    }

    override fun onStart() {

        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        initFlows()
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun initFlows(){



    }

}