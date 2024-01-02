package com.puzzle_agency.androidknowledge.knowledge.auth

object AuthCredentialsValidator {

    private const val MIN_PASSWORD_LENGTH = 8

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(passwordValue: String): Boolean {
        if (passwordValue.length < MIN_PASSWORD_LENGTH) return false
        if (!passwordValue.any { it.isDigit() }) return false
        if (!passwordValue.any { it.isUpperCase() }) return false
        if (!passwordValue.any { it.isLowerCase() }) return false

        return true
    }
}
