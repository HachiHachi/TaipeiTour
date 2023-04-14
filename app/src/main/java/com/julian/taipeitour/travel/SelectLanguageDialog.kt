package com.julian.taipeitour.travel

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.julian.taipeitour.R
import com.julian.taipeitour.databinding.DialogSelectLanguageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectLanguageDialog: DialogFragment(R.layout.dialog_select_language) {

    private lateinit var binding: DialogSelectLanguageBinding

    private var languageArray = mutableListOf<LanguageItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogSelectLanguageBinding.bind(view)

        setArray()
    }

    private fun setArray() {
        val lang = requireActivity().resources.getStringArray(R.array.country_code_key)
        val code = requireActivity().resources.getStringArray(R.array.country_code_value)

        if (lang.size != code.size) return

        lang.forEachIndexed { index, _ ->
            languageArray.add(LanguageItem(lang[index], code[index]))
        }

        binding.rvLanguage.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SelectLanguageAdapter(languageArray, object : SelectLanguageAdapter.ItemClickListener{
                override fun onItemClick(languageItem: LanguageItem) {
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("key", languageItem.code)
                    dismissAllowingStateLoss()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }


    data class LanguageItem(val lang: String, val code: String)
}