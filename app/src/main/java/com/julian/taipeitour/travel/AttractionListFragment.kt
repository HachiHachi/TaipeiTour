package com.julian.taipeitour.travel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.julian.taipeitour.R
import com.julian.taipeitour.common.ex.ResEx.string
import com.julian.taipeitour.common.ui.BaseFragment
import com.julian.taipeitour.databinding.FragmentAttractionListBinding
import com.julian.taipeitour.domain.AttractionsResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttractionListFragment: BaseFragment<FragmentAttractionListBinding>() {

    private val viewModel: AttractionListViewModel by viewModels()
    private var lastVisibleItem: Int = 0
    private var linearLayoutManager: LinearLayoutManager? = null

    private lateinit var attractionAdapter: AttractionsAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAttractionListBinding {
        return FragmentAttractionListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initAdapter()
        initObserver()

        //預設第1筆
        viewModel.setAttractionQuery(resources.getStringArray(R.array.country_code_value)[0], 1)
        viewModel.getAttractionList()
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
                    R.id.menu_news -> {
                        goToNews()
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
        }
    }

    private fun initObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) {

            when(it) {
                is AttractionListViewModel.AttractionState.HandleLoadingProgress -> {
                    showProgress(it.showing)
                }

                is AttractionListViewModel.AttractionState.ShowAttractionFail -> {
                    Toast.makeText(context, string(R.string.something_error_msg), Toast.LENGTH_SHORT).show()
                    attractionAdapter.submitList(viewModel.dataList)
                }

                is AttractionListViewModel.AttractionState.RefreshAttractionList -> {
                    attractionAdapter.submitList(viewModel.dataList)
                }

                is AttractionListViewModel.AttractionState.NextToDetail -> {
                    findNavController().navigate(R.id.action_AttractionListFragment_to_AttractionDetailFragment, it.bundle)
                }
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<String>("key")
            ?.observe(viewLifecycleOwner) { result ->
                if (result == viewModel.attractionQuery.first) return@observe
                viewModel.clearData(result)
                viewModel.getAttractionList()
        }
    }

    private fun initAdapter() {
        binding.rvTravelAttraction.apply {
            attractionAdapter = AttractionsAdapter(object : AttractionsAdapter.ItemClickListener {
                override fun onItemClick(attractionsData: AttractionsResponse.AttractionsData) {
                    viewModel.goToDetail(attractionsData)
                }
            })

            linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

            //監聽滑動事件
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (linearLayoutManager == null) return

                    val isScrollToBottom =
                        newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == attractionAdapter.itemCount

                    viewModel.apply {
                        if (dataList.isNotEmpty() && !isLoadFinish && isScrollToBottom) {
                            viewModel.getAttractionList()
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    lastVisibleItem = linearLayoutManager!!.findLastVisibleItemPosition()
                }
            })

            adapter = attractionAdapter
            layoutManager = linearLayoutManager
        }
    }

    private fun showLangDialog() {
        findNavController().navigate(R.id.action_AttractionListFragment_to_SelectLanguageDialog)
    }

    private fun goToNews() {
        findNavController().navigate(R.id.action_AttractionListFragment_to_NewsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.uiState.value = null
    }
}