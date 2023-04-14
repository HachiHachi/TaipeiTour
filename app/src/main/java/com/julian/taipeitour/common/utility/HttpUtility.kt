package com.julian.taipeitour.common.utility

import retrofit2.Response

object HttpUtility {
    fun <T> result(response: Response<T>) =
        if (response.isSuccessful) {
            Result.success(response.body() ?: throw NullPointerException())
        } else {
            Result.failure(Exception())
        }
}