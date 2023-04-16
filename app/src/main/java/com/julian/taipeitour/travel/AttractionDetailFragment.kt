package com.julian.taipeitour.travel

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.julian.taipeitour.MainActivity
import com.julian.taipeitour.R
import com.julian.taipeitour.common.ex.ResEx.string
import com.julian.taipeitour.common.ui.BaseFragment
import com.julian.taipeitour.databinding.FragmentAttractionDetailBinding
import com.julian.taipeitour.domain.AttractionsResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttractionDetailFragment: BaseFragment<FragmentAttractionDetailBinding>() {

    private lateinit var attractionData: AttractionsResponse.AttractionsData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attractionData = arguments?.getSerializable(MainActivity.BUNDLE_KEY) as AttractionsResponse.AttractionsData
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
                findNavController().navigate(R.id.action_AttractionDetailFragment_to_WebView, bundle)
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
            inflateMenu(R.menu.menu_detail)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_image -> {
                        showDetailDialog()
                        true
                    }
                    R.id.menu_map -> {
                        showMapPromptDialog()
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
        }
    }

    private fun showMapPromptDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(string(R.string.go_to_map_alert_content))
            .setNegativeButton(string(R.string.cancel), DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .setPositiveButton(string(R.string.ok), DialogInterface.OnClickListener { dialog, which ->
                goToMap()
                dialog.dismiss()
            }).show()
    }

    private fun showDetailDialog() {
        val bundle = bundleOf(MainActivity.BUNDLE_KEY to attractionData)
        findNavController().navigate(R.id.action_AttractionDetailFragment_to_AttractionDetailDialog, bundle)
    }

    private fun goToMap() {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(
            "https://www.google.com/maps/search/?api=1&query=" +
                    attractionData.nlat + "," + attractionData.elong
        )
        startActivity(intent)
    }
}