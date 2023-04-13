package com.julian.taipeitour.common.http

import com.julian.taipeitour.domain.TravelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitManager {
    val TAG: String = RetrofitManager::class.java.simpleName

    private const val BASE_URL = "ht" + "tps://www.travel.taipei/open-api/"

    @Singleton
    @Provides
    private fun provideRetrofit(): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())

        return builder.build()
    }


    @Singleton
    @Provides
    fun provideTravelApi(retrofit: Retrofit): TravelApi = retrofit.create(TravelApi::class.java)

}