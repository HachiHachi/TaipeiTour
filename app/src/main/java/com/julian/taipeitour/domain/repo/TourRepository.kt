package com.julian.taipeitour.domain.repo

import android.util.Log
import com.julian.taipeitour.common.IoDispatcher
import com.julian.taipeitour.common.utility.HttpUtility
import com.julian.taipeitour.common.utility.HttpUtility.result
import com.julian.taipeitour.domain.AttractionsResponse
import com.julian.taipeitour.domain.TravelApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


class TourRepository @Inject constructor(
    private val api: TravelApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun getAttractionList(lang: String, page: Int): Result<AttractionsResponse> {
        return withContext(dispatcher) {
            try {
                val response = api.getAttractionsList(lang, page)
                result(response)
            } catch (throwable: Throwable) {
                Result.failure(throwable)
            }
        }
    }
}