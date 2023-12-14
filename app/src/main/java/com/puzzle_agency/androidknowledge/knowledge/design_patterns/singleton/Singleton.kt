package com.puzzle_agency.androidknowledge.knowledge.design_patterns.singleton


/**
 * Singleton rules:
 *  - Private constructor
 *  - Static reference of its class
 *  - Globally accessible object reference
 *  - Consistency across multiple threads
 */

object Singleton {
    init {
        println("Hello Singleton")
    }

    fun provideMessage(): String = "Android knowledge"
}

