package com.julian.taipeitour.domain

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelApi {

    //取得遊憩景點
    @GET("{lang}/Attractions/All")
    suspend fun getAttractionsList(
        @Path("lang") lang: String,
        @Query("page") page: Int
    ): AttractionsResponse

}