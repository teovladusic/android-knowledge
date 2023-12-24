package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.header

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.state_holder.SampleHeaderAction
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.state_holder.SampleHeaderState

@Stable
class HeaderState(
    val uiState: SampleHeaderState,
    val backgroundColor: Color,
    val executeAction: (SampleHeaderAction) -> Unit,
) {

    fun onUserInitialsClick() {
        executeAction(SampleHeaderAction.UserInitialsClick)
    }

    fun queryChanged(query: String) {
        executeAction(SampleHeaderAction.SearchQueryChanged(query))
    }

    fun onSearchActiveChange(isActive: Boolean) {
        executeAction(SampleHeaderAction.SearchActiveChanged(isActive))
    }

    fun onStatsClick() {
        executeAction(SampleHeaderAction.StatsClick)
    }

    fun onCardsClick() {
        executeAction(SampleHeaderAction.CardsClick)
    }
}

@Composable
fun rememberHeaderState(
    uiState: SampleHeaderState,
    backgroundColor: Color,
    executeAction: (SampleHeaderAction) -> Unit,
): HeaderState {
    return remember(uiState, backgroundColor, executeAction) {
        HeaderState(uiState, backgroundColor, executeAction)
    }
}
