package com.puzzle_agency.androidknowledge.knowledge.design_patterns.singleton

@Suppress("Unused")
fun useSingleton() {
    val message = Singleton.provideMessage()
    println(message)
}