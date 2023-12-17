package com.puzzle_agency.androidknowledge.knowledge.network.retrofit.repository

import com.puzzle_agency.androidknowledge.knowledge.network.retrofit.SampleApi
import com.puzzle_agency.androidknowledge.knowledge.network.retrofit.domain.Result
import com.puzzle_agency.androidknowledge.knowledge.network.retrofit.domain.SampleResult
import com.puzzle_agency.androidknowledge.knowledge.network.retrofit.domain.handleErrorAndMapSuccess

class SampleRepository(private val api: SampleApi) {
    suspend fun getSample(): Result<SampleResult> = api.getSample().handleErrorAndMapSuccess {
        SampleResult(it.data?.message ?: "")
    }
}