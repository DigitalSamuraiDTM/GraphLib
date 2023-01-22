package com.digitalsamurai.graphlib.ui.libs

import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.R
import com.digitalsamurai.graphlib.databinding.FragmentLibsBinding
import com.digitalsamurai.graphlib.ui.main.MainFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class LibsFragment : Fragment() {

    private lateinit var binding : FragmentLibsBinding

    @Inject
    lateinit var viewModel : LibsViewModel

    private lateinit var adapter: LibsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLibsBinding.inflate(inflater,container,false)

        binding.libsRecyclerData.layoutManager = LinearLayoutManager(this.requireContext()).also {
            it.orientation = LinearLayoutManager.VERTICAL

        }

        binding.libsFabAddLib.setOnClickListener{
            findNavController().navigate(R.id.action_libsFragment_to_libCreatingDialog)
        }


        adapter = LibsAdapter(viewModel)
        binding.libsRecyclerData.adapter = adapter


        GraphLibApp.appComponent.injectLibsFragment(this)
        viewModel.initLibsViewModel()


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

        lifecycleScope.launch{
            viewModel.selectedLibFlow.collectLatest {libName ->
                findNavController().navigate(R.id.action_libsFragment_to_mainFragment,
                    Bundle().also {
                    it.putString(MainFragment.BUNDLE_LIB_NAME,libName)
                 })

            }
        }

        lifecycleScope.launch {
            viewModel.libsFlow.collect {
                if (it.isEmpty()){
                    binding.libsTextViewFak.text = "FAK EMPTY"
                } else{
                    binding.libsTextViewFak.text = "Select ur lib"
                }
                adapter.submitData(PagingData.from(it))
            }
        }

    }

}