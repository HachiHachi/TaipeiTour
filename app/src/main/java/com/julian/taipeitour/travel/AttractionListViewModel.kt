package com.julian.taipeitour.travel

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julian.taipeitour.MainActivity
import com.julian.taipeitour.domain.AttractionsResponse
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

    var dataList  = mutableListOf<AttractionsResponse.AttractionsData>()

    var isLoadFinish: Boolean = false

    //Pair<language,page>
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

    private fun handleAttractionData(data: List<AttractionsResponse.AttractionsData>?) {
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

    fun goToDetail(attractionsData: AttractionsResponse.AttractionsData) {
        val bundle = bundleOf(MainActivity.BUNDLE_KEY to attractionsData)
        uiState.value = AttractionState.NextToDetail(bundle)
    }

    /**
     *  View的狀態
     */
    sealed class AttractionState {

        object ShowAttractionFail: AttractionState()

        object RefreshAttractionList: AttractionState()

        data class HandleLoadingProgress(val showing: Boolean): AttractionState()

        data class NextToDetail(val bundle: Bundle): AttractionState()
    }
}