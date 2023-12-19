package com.puzzle_agency.androidknowledge.knowledge.local_storage.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SampleDao {

    @Upsert
    suspend fun insertSample(sampleEntity: SampleEntity)

    @Delete
    suspend fun deleteSample(sampleEntity: SampleEntity)

    @Query("SELECT * FROM sampleentity")
    fun getSamples(): Flow<List<SampleEntity>>

    @Query("SELECT * FROM sampleentity WHERE name LIKE '%' || :query || '%'")
    fun searchSamples(query: String): Flow<List<SampleEntity>>
}