package com.julian.taipeitour.travel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.RoundedCornersTransformation
import com.julian.taipeitour.R
import com.julian.taipeitour.common.ex.ResEx.string
import com.julian.taipeitour.common.ui.EmptyViewAdapter
import com.julian.taipeitour.databinding.AdapterAttractionListBinding
import com.julian.taipeitour.domain.AttractionsResponse

class AttractionsAdapter(var itemClick: ItemClickListener) : EmptyViewAdapter() {

    var attractionsList = mutableListOf<AttractionsResponse.AttractionsData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == ITEM_TYPE_EMPTY) {
            return EmptyViewHolder(getEmptyView(parent))
        }

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdapterAttractionListBinding.inflate(layoutInflater, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is EmptyViewAdapter.EmptyViewHolder -> {
                holder.setIcon(R.drawable.ic_problem)
                holder.setTitle(string(R.string.no_data))
            }

            is AttractionsAdapter.Holder -> {
                holder.bind(attractionsList[position])
            }
        }
    }

    override fun getDataCount(): Int {
        return attractionsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(itemAttractions: List<AttractionsResponse.AttractionsData>) {
        this.attractionsList = itemAttractions.toMutableList()
        notifyDataSetChanged()
    }


    inner class Holder(private val binding: AdapterAttractionListBinding) : ViewHolder(binding.root) {

        fun bind(attractionsData: AttractionsResponse.AttractionsData) {
            binding.data = attractionsData
            binding.imgAttraction.load(attractionsData.images.getOrNull(0)?.src) {
                crossfade(true)
                placeholder(R.drawable.image_default)
                error(R.drawable.image_default)
                transformations(RoundedCornersTransformation(10f))
            }
        }

        init {
            itemView.setOnClickListener {
                itemClick.onItemClick(attractionsList[adapterPosition])
            }
        }
    }

    interface ItemClickListener {
        fun onItemClick(attractionsData: AttractionsResponse.AttractionsData)
    }
}
