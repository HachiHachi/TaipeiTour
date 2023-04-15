package com.julian.taipeitour.travel

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.julian.taipeitour.R
import com.youth.banner.adapter.BannerAdapter

class AttractionBannerAdapter(data: List<String> = mutableListOf()):
    BannerAdapter<String, AttractionBannerAdapter.ImgBannerViewHolder>(data) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ImgBannerViewHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return ImgBannerViewHolder(imageView)
    }

    override fun onBindView(holder: ImgBannerViewHolder, data: String?, position: Int, size: Int) {
        (holder.itemView as ImageView).load(data) {
            crossfade(true)
            scale(Scale.FILL)
            error(R.drawable.image_default)
            transformations(RoundedCornersTransformation())
        }
    }

    class ImgBannerViewHolder(img: ImageView) : RecyclerView.ViewHolder(img)

}