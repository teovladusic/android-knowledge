package com.puzzle_agency.androidknowledge.knowledge.local_storage.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SampleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)