package com.julian.taipeitour.travel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.julian.taipeitour.databinding.AdapterSelectLanguageBinding

class SelectLanguageAdapter(val list: MutableList<SelectLanguageDialog.LanguageItem>,
                            val itemClick: ItemClickListener)
    : RecyclerView.Adapter<SelectLanguageAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdapterSelectLanguageBinding.inflate(layoutInflater, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
       holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(private val binding: AdapterSelectLanguageBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(languageItem: SelectLanguageDialog.LanguageItem) {
             binding.data = languageItem
        }

        init {
            itemView.setOnClickListener {
                itemClick.onItemClick(list[adapterPosition])
            }
        }
    }

    interface ItemClickListener {
        fun onItemClick(languageItem: SelectLanguageDialog.LanguageItem)
    }
}