package com.julian.taipeitour.travel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.julian.taipeitour.databinding.AdapterNewsBinding
import com.julian.taipeitour.domain.NewsResponse

@SuppressLint("NotifyDataSetChanged")
class NewsAdapter(private val itemClickListener: ItemClickListener): RecyclerView.Adapter<NewsAdapter.Holder>() {

    var newsData = mutableListOf<NewsResponse.NewsData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdapterNewsBinding.inflate(layoutInflater, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(newsData[position])
    }

    override fun getItemCount(): Int {
        return newsData.size
    }


    fun submitList(data: List<NewsResponse.NewsData>) {
        this.newsData = data.toMutableList()
        notifyDataSetChanged()
    }

    fun updateList(data: NewsResponse.NewsData, position: Int) {
        this.newsData[position] = data
        notifyItemChanged(position)
    }

    inner class Holder(private val bind: AdapterNewsBinding) : RecyclerView.ViewHolder(bind.root) {

        fun bind(response: NewsResponse.NewsData) {
            bind.data = response
            bind.tvNewsContent.visibility = if (response.isExpend) View.VISIBLE else View.GONE
        }

        init {
            itemView.setOnClickListener {
                itemClickListener.onItemClick(newsData[adapterPosition], adapterPosition)
            }
        }
    }

    interface ItemClickListener {
        fun onItemClick(newsData: NewsResponse.NewsData, pos: Int)
    }
}