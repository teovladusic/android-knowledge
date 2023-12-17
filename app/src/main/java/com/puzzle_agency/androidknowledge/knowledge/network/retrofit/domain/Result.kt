package com.puzzle_agency.androidknowledge.knowledge.network.retrofit.domain

import com.puzzle_agency.androidknowledge.knowledge.network.retrofit.api_result.ApiResult

sealed interface Result<out T> {
    data class Success<T>(val value: T) : Result<T>
    data class Failure(
        val throwable: Throwable? = null,
        val message: String? = null,
        val networkErrorType: NetworkErrorType? = null
    ) : Result<Nothing>
}

inline fun <T, K> ApiResult<K>.handleErrorAndMapSuccess(
    crossinline mapSuccess: (ApiResult.Success<K>) -> T
): Result<T> = when (this) {
    is ApiResult.Error -> Result.Failure(message = message, networkErrorType = networkErrorType)
    is ApiResult.Exception -> Result.Failure(throwable = throwable)
    is ApiResult.Success -> Result.Success(mapSuccess(this))
}