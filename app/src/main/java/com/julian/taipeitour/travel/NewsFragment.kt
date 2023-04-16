package com.julian.taipeitour.travel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.julian.taipeitour.R
import com.julian.taipeitour.common.ex.ResEx.string
import com.julian.taipeitour.common.ui.BaseFragment
import com.julian.taipeitour.databinding.FragmentNewsBinding
import com.julian.taipeitour.domain.NewsResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment: BaseFragment<FragmentNewsBinding>() {

    private val newsModel: NewsModel by viewModels()

    private lateinit var newsAdapter: NewsAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewsBinding {
        return FragmentNewsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initAdapter()
        initObserver()

        //預設第1筆
        newsModel.setQuery(resources.getStringArray(R.array.country_code_value)[0], 1)
        newsModel.getNews()

    }

    private fun initObserver() {
        newsModel.uiState.observe(viewLifecycleOwner) {

            when(it) {

                is NewsModel.NewsUIState.HandleLoadingProgress -> {
                    showProgress(it.showing)
                }

                is NewsModel.NewsUIState.ShowNewsFail -> {
                    Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                    newsAdapter.submitList(newsModel.dataList)
                }

                is NewsModel.NewsUIState.RefreshNewsList -> {
                    newsAdapter.submitList(newsModel.dataList)
                }
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<String>("key")
            ?.observe(viewLifecycleOwner) { result ->
                if (result == newsModel.query.first) return@observe
                newsModel.clearData(result)
                newsModel.getNews()
            }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            title = string(R.string.toolbar_title_city)
            inflateMenu(R.menu.menu_main)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_lang -> {
                        showLangDialog()
                        true
                    }
                    else -> {
                        true
                    }
                }
            }

            setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            menu.findItem(R.id.menu_news).isVisible = false

        }
    }

    private fun initAdapter() {
        binding.rvNews.apply {
            newsAdapter = NewsAdapter(object : NewsAdapter.ItemClickListener{
                override fun onItemClick(newsData: NewsResponse.NewsData, pos: Int) {
                    newsData.isExpend = !newsData.isExpend
                    newsAdapter.updateList(newsData, pos)
                }
            })

            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = newsAdapter
        }
    }


    private fun showLangDialog() {
        findNavController().navigate(R.id.action_NewsFragment_to_SelectLanguageDialog)
    }
}