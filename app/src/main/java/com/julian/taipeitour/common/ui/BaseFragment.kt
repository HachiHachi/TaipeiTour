package com.julian.taipeitour.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.julian.taipeitour.common.utility.DialogUtility

abstract class BaseFragment<B: ViewDataBinding>: Fragment() {

    protected lateinit var binding: B

    protected lateinit var progressDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = getFragmentBinding(inflater, container) as B
        return binding.root
    }

    abstract fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): B


    fun showProgress(b: Boolean) {
        requireActivity().runOnUiThread {
            if (!this::progressDialog.isInitialized) {
                progressDialog = DialogUtility.progressDialog(requireActivity())
            }

            if (b) progressDialog.show() else progressDialog.dismiss()
        }
    }
}