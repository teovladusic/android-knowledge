package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.domain

import java.time.LocalDateTime

data class Transaction(
    val id: Int,
    val imageUrl: String?,
    val username: String,
    val dateTime: LocalDateTime,
    val amount: Double,
)