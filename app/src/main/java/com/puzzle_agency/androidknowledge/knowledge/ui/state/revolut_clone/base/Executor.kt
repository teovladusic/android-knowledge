package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.base

interface Executor<Action> {
    operator fun invoke(action: Action)
}