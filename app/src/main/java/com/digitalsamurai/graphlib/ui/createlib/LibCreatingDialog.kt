package com.digitalsamurai.graphlib.ui.createlib

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.databinding.DialogCreatelibBinding
import com.digitalsamurai.graphlib.ui.libs.LibsViewModel
import javax.inject.Inject

class LibCreatingDialog : DialogFragment() {
    private lateinit var binding : DialogCreatelibBinding

    @Inject
    lateinit var database: GraphDatabase

    @Inject
    lateinit var viewModel : LibsViewModel

    init {
        GraphLibApp.appComponent.injectLibsFragment(this)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            binding = DialogCreatelibBinding.inflate(LayoutInflater.from(this.context))
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)




            binding.createlibBtnCreate.setOnClickListener {
                if (binding.createlibEditLibname.text.isNotBlank()){

                    viewModel.insertLib(binding.createlibEditLibname.text.toString())
                    this@LibCreatingDialog.dismiss()

                }
            }

            builder.create()
        } ?: throw IllegalStateException("FAK")
    }
}