package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.state_holder

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SampleHeaderStateHolder(private val coroutineScope: CoroutineScope) {

    private val _state: MutableStateFlow<SampleHeaderState> =
        MutableStateFlow(SampleHeaderState())

    val state: StateFlow<SampleHeaderState> = _state.asStateFlow()

    fun executeAction(action: SampleHeaderAction) {
        when (action) {
            SampleHeaderAction.UserInitialsClick -> {
                Log.d("SampleHeaderStateHolder", "navigate to user profile")
            }

            is SampleHeaderAction.SearchActiveChanged -> _state.update { state ->
                state.copy(isSearchActive = action.isActive)
            }

            is SampleHeaderAction.SearchQueryChanged -> onSearchQueryChanged(action.value)

            SampleHeaderAction.StatsClick -> {
                Log.d("SampleHeaderStateHolder", "navigate to stats")
            }

            SampleHeaderAction.CardsClick -> {
                Log.d("SampleHeaderStateHolder", "navigate to cards")
            }
        }
    }

    init {
        fetchUserInitials()
    }

    private fun onSearchQueryChanged(query: String) {
        _state.update { state ->
            state.copy(searchQuery = query)
        }

        coroutineScope.launch {
            val results = fetchResults(query)

            _state.update {
                it.copy(searchResults = results)
            }
        }
    }

    private val firstNames = listOf("Alice", "Bob", "Charlie", "David", "Eva", "Frank")

    @Suppress("detekt.MagicNumber")
    private suspend fun fetchResults(query: String): List<String> {
        if (query.isBlank()) return emptyList()

        delay(500)

        return firstNames.filter { firstName -> firstName.contains(query, ignoreCase = true) }
    }

    @Suppress("detekt.MagicNumber")
    private fun fetchUserInitials() {
        coroutineScope.launch {
            delay(500)

            _state.update {
                it.copy(userInitials = "AK")
            }
        }
    }
}

sealed interface SampleHeaderAction {
    data class SearchQueryChanged(val value: String) : SampleHeaderAction
    data class SearchActiveChanged(val isActive: Boolean) : SampleHeaderAction
    data object UserInitialsClick : SampleHeaderAction
    data object StatsClick : SampleHeaderAction
    data object CardsClick : SampleHeaderAction
}

data class SampleHeaderState(
    val searchQuery: String = "",
    val isSearchActive: Boolean = false,
    val userInitials: String = "",
    val searchResults: List<String> = emptyList()
)
