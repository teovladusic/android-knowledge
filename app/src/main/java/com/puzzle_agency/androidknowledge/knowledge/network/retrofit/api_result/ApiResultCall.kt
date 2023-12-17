package com.puzzle_agency.androidknowledge.knowledge.network.retrofit.api_result

import android.util.Log
import com.google.gson.Gson
import com.puzzle_agency.androidknowledge.knowledge.network.retrofit.domain.NetworkErrorType
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class ApiResultCall<T>(
    private val callDelegate: Call<T>,
) : Call<ApiResult<T>> {

    @Suppress("detekt.MagicNumber")
    override fun enqueue(callback: Callback<ApiResult<T>>) = callDelegate.enqueue(
        object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                when (val code = response.code()) {
                    in 200..208 -> {
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(ApiResult.Success(response.body()))
                        )
                    }

                    in 400..409 -> callback.onResponse(
                        this@ApiResultCall,
                        handleErrorStatusCode(response, code)
                    )

                    else -> {
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(
                                ApiResult.Error(
                                    code = code,
                                    message = null,
                                    networkErrorType = null
                                )
                            ),
                        )
                    }
                }
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                callback.onResponse(
                    this@ApiResultCall,
                    Response.success(ApiResult.Exception(throwable))
                )
                call.cancel()
            }
        },
    )

    override fun clone(): Call<ApiResult<T>> = ApiResultCall(callDelegate.clone())

    override fun execute(): Response<ApiResult<T>> =
        throw UnsupportedOperationException("ResponseCall does not support execute.")

    override fun isExecuted(): Boolean = callDelegate.isExecuted

    override fun cancel() = callDelegate.cancel()

    override fun isCanceled(): Boolean = callDelegate.isCanceled

    override fun request(): Request = callDelegate.request()

    override fun timeout(): Timeout = callDelegate.timeout()

    private fun handleErrorStatusCode(
        response: Response<T>,
        code: Int
    ): Response<ApiResult<T>> {
        val errorBody = try {
            Gson().fromJson(response.errorBody()?.string(), ErrorBody::class.java)
        } catch (e: Exception) {
            Log.d("ApiResultCall", e.message.toString())
            null
        }

        return Response.success(
            ApiResult.Error(
                code = code,
                message = errorBody?.message ?: response.message().ifBlank { null },
                networkErrorType = NetworkErrorType.fromStatusCode(code)
            )
        )
    }
}

data class ErrorBody(
    val message: String,
    val status: Int
)