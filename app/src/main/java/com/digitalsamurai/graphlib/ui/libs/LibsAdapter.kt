package com.digitalsamurai.graphlib.ui.libs

import android.app.ActionBar
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.digitalsamurai.graphlib.database.room.libs.Lib
import com.digitalsamurai.graphlib.databinding.ItemLibBinding
import com.digitalsamurai.graphlib.ui.createlib.LibItemViewModel

class LibsAdapter(private val viewModel : LibItemViewModel) : PagingDataAdapter<Lib,LibsAdapter.LibViewHolder>(LibDiffUtilCallback) {

    override fun onBindViewHolder(holder: LibViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindInfo(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibViewHolder {
        return LibViewHolder(ItemLibBinding.inflate(LayoutInflater.from(parent.context)),viewModel)
    }

    inner class LibViewHolder(private val binding: ItemLibBinding,
                              private val viewModel: LibItemViewModel) : ViewHolder(binding.root){
        lateinit var data : Lib

        fun bindInfo(info : Lib){
            binding.root.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT)
                .also {
                    it.setMargins(10,10,10,10)
                }
            this.data = info
            binding.itemLibTextViewLibName.text = data.libName
            binding.root.setOnClickListener{
                viewModel.selectLib(this.data.libName)
            }
        }

    }

    object LibDiffUtilCallback : DiffUtil.ItemCallback<Lib>() {
        override fun areItemsTheSame(oldItem: Lib, newItem: Lib): Boolean {
            return oldItem.libName==newItem.libName
        }

        override fun areContentsTheSame(oldItem: Lib, newItem: Lib): Boolean {
            return oldItem.libName==newItem.libName && oldItem.dateCreating==newItem.dateCreating
        }
    }
}