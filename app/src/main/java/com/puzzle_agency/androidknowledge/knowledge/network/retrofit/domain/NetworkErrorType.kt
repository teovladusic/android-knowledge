package com.puzzle_agency.androidknowledge.knowledge.network.retrofit.domain

@Suppress("detekt.MagicNumber")
enum class NetworkErrorType {
    BadRequest,
    Unauthorized,
    Forbidden,
    NotFound;

    companion object {
        fun fromStatusCode(code: Int) = when (code) {
            400 -> BadRequest
            401 -> Unauthorized
            403 -> Forbidden
            404 -> NotFound
            else -> null
        }
    }
}