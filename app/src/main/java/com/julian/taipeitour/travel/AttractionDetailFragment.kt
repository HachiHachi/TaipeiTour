package com.julian.taipeitour.travel

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.julian.taipeitour.MainActivity
import com.julian.taipeitour.R
import com.julian.taipeitour.common.ex.ResEx.drawable
import com.julian.taipeitour.common.ui.BaseFragment
import com.julian.taipeitour.databinding.FragmentAttractionDetailBinding
import com.julian.taipeitour.domain.AttractionsData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttractionDetailFragment: BaseFragment<FragmentAttractionDetailBinding>() {

    private lateinit var attractionData: AttractionsData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attractionData = arguments?.getSerializable(MainActivity.BUNDLE_KEY) as AttractionsData
        binding.data = attractionData

        initTooBar()


        binding.apply {
            ivBanner.load(attractionData.images.getOrNull(0)?.src) {
                crossfade(true)
                scale(Scale.FILL)
                placeholder(R.drawable.image_default)
                error(R.drawable.image_default)
                transformations(RoundedCornersTransformation())
            }

            tvAttractionUrl.paintFlags = tvAttractionUrl.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            tvAttractionUrl.setOnClickListener {
                val bundle = bundleOf(MainActivity.BUNDLE_KEY to attractionData.url)
                findNavController().navigate(R.id.action_AttractionDetail_to_WebView, bundle)
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAttractionDetailBinding {
        return FragmentAttractionDetailBinding.inflate(inflater, container, false)
    }


    private fun initTooBar() {

        binding.toolbar.apply {
            title = attractionData.name
            navigationIcon = drawable(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}