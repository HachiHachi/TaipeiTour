package com.julian.taipeitour.travel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julian.taipeitour.domain.NewsResponse
import com.julian.taipeitour.domain.repo.TourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsModel @Inject constructor(
    private val repository: TourRepository
)  : ViewModel() {

    val uiState = MutableLiveData<NewsUIState>()

    lateinit var query: Pair<String, Int>

    var dataList  = mutableListOf<NewsResponse.NewsData>()

    fun getNews() {
        viewModelScope.launch {
            uiState.value = NewsUIState.HandleLoadingProgress(true)

            val result = repository.getNews(query.first, query.second)

            if (result.isSuccess) {
                handleNewsData(result.getOrNull()?.data)
            } else {
                uiState.value = NewsUIState.ShowNewsFail("Error")
            }

            uiState.value = NewsUIState.HandleLoadingProgress(false)
        }
    }

    private fun handleNewsData(data: List<NewsResponse.NewsData>?) {
//        if (data.isNullOrEmpty()) {
//            uiState.value = NewsUIState.ShowNewsFail("No Data")
//            return
//        }
        //目前不實作分批取資料,可擴充
        data.apply {
            data?.let { dataList.addAll(it) }
            uiState.value = NewsUIState.RefreshNewsList
        }
    }

    fun setQuery(lang: String, page: Int) {
        query = Pair(lang, page)
    }

    fun clearData(lang: String) {
        dataList = mutableListOf()
        setQuery(lang, 1)
    }

    sealed class NewsUIState {

        data class HandleLoadingProgress(val showing: Boolean): NewsUIState()

        data class ShowNewsFail(val msg: String): NewsUIState()

        object RefreshNewsList: NewsUIState()
    }
}