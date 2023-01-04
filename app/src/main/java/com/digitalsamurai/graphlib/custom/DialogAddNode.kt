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
import com.digitalsamurai.graphlib.math.tree.TreeNode

class DialogAddNode(private val inter : CreateInterface) : DialogFragment() {
    private lateinit var binding : DialogAddNodeBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            binding = DialogAddNodeBinding.inflate(LayoutInflater.from(this.context))
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)

            val nodes = MainActivity.currentNodesList

            val spinner = binding.spinner
            val adapter = ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_item,nodes.map { it.data.text.toString() })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            binding.btnFinish.setOnClickListener {
                val selectedSpinner = binding.spinner.selectedItemPosition
                val parent = MainActivity.currentNodesList.get(selectedSpinner)
                inter.created(
                    binding.x.text.toString().toInt(),
                    binding.y.text.toString().toInt(),
                    binding.data.text.toString(),
                    parent)
                this.dismiss()
            }

            builder.create()
        } ?: throw IllegalStateException("FAK")
    }
}