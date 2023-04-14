package com.julian.taipeitour.travel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julian.taipeitour.domain.AttractionsData
import com.julian.taipeitour.domain.repo.TourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttractionListViewModel @Inject constructor(
    private val repository: TourRepository
) : ViewModel() {
    private val TAG = AttractionListViewModel::class.java.simpleName

    var uiState = MutableLiveData<AttractionState>()

    var dataList  = mutableListOf<AttractionsData>()

    var isLoadFinish: Boolean = false

    lateinit var attractionQuery: Pair<String, Int>

    fun getAttractionList() {
        viewModelScope.launch {
            uiState.value = AttractionState.HandleLoadingProgress(true)

            val result = repository.getAttractionList(attractionQuery.first, attractionQuery.second)

            if (result.isSuccess) {
                handleAttractionData(result.getOrNull()?.data)
            } else {
                uiState.value = AttractionState.ShowAttractionFail
            }

            uiState.value = AttractionState.HandleLoadingProgress(false)
        }
    }

    private fun handleAttractionData(data: List<AttractionsData>?) {
        Log.d(TAG, "Attraction:" + "${data?.size}")
        if (data.isNullOrEmpty() || data.size < 30) {
            isLoadFinish = true
            return
        }

        data.apply {
            setAttractionQuery(attractionQuery.first, attractionQuery.second + 1)
            dataList.addAll(data)
            uiState.value = AttractionState.RefreshAttractionList
        }
    }

    fun setAttractionQuery(lang: String, page: Int) {
        attractionQuery = Pair(lang, page)
    }

    fun clearData(lang: String) {
        dataList = mutableListOf()
        setAttractionQuery(lang, 1)
    }

    /**
     *  View的狀態
     */
    sealed class AttractionState {

        object ShowAttractionFail: AttractionState()

        object RefreshAttractionList: AttractionState()

        data class HandleLoadingProgress(val showing: Boolean): AttractionState()

    }
}