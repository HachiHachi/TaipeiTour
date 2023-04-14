package com.julian.taipeitour.travel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.julian.taipeitour.MainActivity
import com.julian.taipeitour.R
import com.julian.taipeitour.common.ex.ResEx.drawable
import com.julian.taipeitour.common.ui.BaseFragment
import com.julian.taipeitour.databinding.FragmentWebviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment: BaseFragment<FragmentWebviewBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWebviewBinding {
        return FragmentWebviewBinding.inflate(inflater, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString(MainActivity.BUNDLE_KEY)

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(url!!)
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)

            webChromeClient = object : WebChromeClient() {

                override fun onReceivedTitle(view: WebView, title: String) {}

                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    binding.progressBarWeb.apply {
                        progress = if (newProgress < 100) newProgress else 0
                        visibility = if (newProgress < 100) View.VISIBLE else View.INVISIBLE
                    }
                }
            }
        }

        binding.toolbar.apply {
            navigationIcon = drawable(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}