package com.julian.taipeitour.domain.repo

import com.julian.taipeitour.common.IoDispatcher
import com.julian.taipeitour.domain.AttractionsResponse
import com.julian.taipeitour.domain.TravelApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class TourRepository @Inject constructor(
    private val api: TravelApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {


    suspend fun getAttractionList(lang: String, page: Int): AttractionsResponse {
        return withContext(dispatcher) {
            api.getAttractionsList(lang, page)
        }
    }

}