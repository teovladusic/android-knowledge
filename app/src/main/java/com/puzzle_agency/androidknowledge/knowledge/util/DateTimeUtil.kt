package com.puzzle_agency.androidknowledge.knowledge.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime.format(): String {
    val pattern = "d. MMM, H:mm"
    val dateFormat = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return dateFormat.format(this)
}
