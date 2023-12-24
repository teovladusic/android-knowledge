package com.puzzle_agency.androidknowledge.knowledge.ui.state

import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.domain.Transaction
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.state_holder.SampleHeaderState
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.widget.HomeWidget

data class SampleUiState(
    val isLoading: Boolean = false,
    val headerState: SampleHeaderState = SampleHeaderState(),
    val searchActive: Boolean = false,
    val transactions: List<Transaction> = emptyList(),
    val widgets: List<HomeWidget> = emptyList(),
)
