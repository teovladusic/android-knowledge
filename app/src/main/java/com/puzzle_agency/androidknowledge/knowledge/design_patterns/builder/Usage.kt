package com.puzzle_agency.androidknowledge.knowledge.design_patterns.builder

@Suppress("Unused")
fun useBuilderPattern() {
    val request = HttpRequest.Builder()
        .setMethod("GET")
        .setUrl("https://google.com")
        .addHeader("Content-Type", "application/json")
        .setBody("Body")
        .build()

    println(request)
}