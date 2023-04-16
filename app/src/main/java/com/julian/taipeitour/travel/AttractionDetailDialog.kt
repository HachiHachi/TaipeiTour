package com.julian.taipeitour.travel

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.julian.taipeitour.MainActivity
import com.julian.taipeitour.R
import com.julian.taipeitour.common.ex.OtherEx.case
import com.julian.taipeitour.databinding.DialogAttractionDetailBinding
import com.julian.taipeitour.domain.AttractionsResponse
import com.youth.banner.Banner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttractionDetailDialog: DialogFragment(R.layout.dialog_attraction_detail) {

    private lateinit var binding: DialogAttractionDetailBinding
    private lateinit var attractionData: AttractionsResponse.AttractionsData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogAttractionDetailBinding.bind(view)

        attractionData = arguments?.getSerializable(MainActivity.BUNDLE_KEY) as AttractionsResponse.AttractionsData
        binding.data = attractionData

        setBanner()
    }

    private fun setBanner() {
        val data = attractionData.let {
            val list = mutableListOf<String>()
            it.images.forEach { image ->
                list.add(image.src)
            }
            list
        }
        binding.banner.apply {
            case<Banner<String, AttractionBannerAdapter>>().setAdapter(AttractionBannerAdapter())
            case<Banner<String, AttractionBannerAdapter>>().setDatas(data)
        }
    }


    override fun onResume() {
        super.onResume()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
}