package com.digitalsamurai.graphlib.custom

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.digitalsamurai.graphlib.CreateInterface
import com.digitalsamurai.graphlib.MainActivity
import com.digitalsamurai.graphlib.R
import com.digitalsamurai.graphlib.databinding.DialogAddNodeBinding
import com.digitalsamurai.graphlib.databinding.DialogCreatelibBinding
import com.digitalsamurai.tree.TreeNode

class DialogAddNode(private val inter : CreateInterface) : DialogFragment() {
    private lateinit var binding : DialogAddNodeBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            binding = DialogAddNodeBinding.inflate(LayoutInflater.from(this.context))
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)




//            binding.btnFinish.setOnClickListener {
//
//            }

            builder.create()
        } ?: throw IllegalStateException("FAK")
    }
}