package com.julian.taipeitour.travel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.julian.taipeitour.R
import com.julian.taipeitour.databinding.AdapterAttractionListBinding
import com.julian.taipeitour.domain.AttractionsData

class AttractionsAdapter(var itemClick: ItemClickListener) :
    RecyclerView.Adapter<AttractionsAdapter.Holder>() {

    var attractionsList = mutableListOf<AttractionsData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdapterAttractionListBinding.inflate(layoutInflater, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(attractionsList[position])
    }

    override fun getItemCount(): Int {
        return attractionsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(itemAttractions: List<AttractionsData>) {
        this.attractionsList = itemAttractions.toMutableList()
        notifyDataSetChanged()
    }


    inner class Holder(private val binding: AdapterAttractionListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(attractionsData: AttractionsData) {
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
        fun onItemClick(attractionsData: AttractionsData)
    }
}
