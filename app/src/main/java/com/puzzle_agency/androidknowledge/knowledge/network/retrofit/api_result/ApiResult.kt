package com.puzzle_agency.androidknowledge.knowledge.network.retrofit.api_result

import com.puzzle_agency.androidknowledge.knowledge.network.retrofit.domain.NetworkErrorType

sealed interface ApiResult<T> {

    /**
     * Represents a network result that successfully received a response containing body data.
     */
    class Success<T>(val data: T?) : ApiResult<T>

    /**
     * Represents a network result that successfully received a response containing an error message.
     */
    class Error<T>(val code: Int, val message: String?, val networkErrorType: NetworkErrorType?) :
        ApiResult<T>

    /**
     * Represents a network result that faced an unexpected exception before getting a response
     * from the network such as IOException and UnKnownHostException.
     */
    class Exception<T>(val throwable: Throwable) : ApiResult<T>
}
