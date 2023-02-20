package com.digitalsamurai.graphlib.ui.createlib

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.digitalsamurai.graphlib.GraphLibApp
import com.digitalsamurai.graphlib.database.room.GraphDatabase
import com.digitalsamurai.graphlib.databinding.DialogCreatelibBinding
import javax.inject.Inject

class LibCreatingDialog : DialogFragment() {
    private lateinit var binding : DialogCreatelibBinding

    @Inject
    lateinit var database: GraphDatabase



    init {
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            binding = DialogCreatelibBinding.inflate(LayoutInflater.from(this.context))
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)






            builder.create()
        } ?: throw IllegalStateException("FAK")
    }
}