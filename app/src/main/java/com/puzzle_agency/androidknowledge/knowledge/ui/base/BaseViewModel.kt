package com.puzzle_agency.androidknowledge.knowledge.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<Event, Action> : ViewModel(), Executor<Action> {
    private var _event: Channel<Event> = Channel()
    val event: Flow<Event>
        get() = _event.receiveAsFlow()

    protected fun onEvent(event: Event) {
        _event.trySend(event)
        Log.d("BaseViewModel", "Event: $event")
    }

    override fun invoke(action: Action) {
        Log.d("BaseViewModel", "Action: $action")
        execute(action)
    }

    abstract fun execute(action: Action)
}
