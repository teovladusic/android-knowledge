package com.puzzle_agency.androidknowledge.knowledge.network.retrofit

import com.puzzle_agency.androidknowledge.knowledge.network.retrofit.api_result.ApiResult
import retrofit2.http.GET

interface SampleApi {
    @GET("sample")
    suspend fun getSample(): ApiResult<SampleResultDto>
}
