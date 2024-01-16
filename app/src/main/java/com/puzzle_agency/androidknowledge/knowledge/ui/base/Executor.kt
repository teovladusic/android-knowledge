package com.puzzle_agency.androidknowledge.knowledge.ui.base

interface Executor<Action> {
    operator fun invoke(action: Action)
}
