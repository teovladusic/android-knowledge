package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.base

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow

interface BaseView<State, Event, Action> {

    @Composable
    fun Compose(
        state: State,
        events: Flow<Event>,
        executor: Executor<Action>,
    )
}