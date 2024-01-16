package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.state_holder

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.SampleAction
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.SampleUiState
import com.puzzle_agency.androidknowledge.knowledge.ui.base.BaseViewModel
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.data.SampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val sampleRepository: SampleRepository
) : BaseViewModel<Unit, SampleAction>() {

    private val headerStateHolder = SampleHeaderStateHolder(viewModelScope)

    companion object {
        private const val TRANSACTIONS_COUNT = 3
    }

    private val _state = MutableStateFlow(SampleUiState())

    val state: StateFlow<SampleUiState> = _state
        .combine(headerStateHolder.state) { state, headerState ->
            state.copy(headerState = headerState)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            SampleUiState()
        )

    override fun execute(action: SampleAction) {
        when (action) {
            SampleAction.AccountsButtonClick ->
                Log.d("SampleViewModel", "navigate to accounts")

            SampleAction.AddWidgetClick ->
                Log.d("SampleViewModel", "navigate to add widget")

            is SampleAction.ParallaxActionButtonClick ->
                Log.d("SampleViewModel", "handle parallax button click ${action.type}")

            SampleAction.SeeAllTransactionsClick ->
                Log.d("SampleViewModel", "navigate to all transactions")

            is SampleAction.TransactionClick ->
                Log.d("SampleViewModel", "navigate to transaction")

            is SampleAction.ExecuteHeaderAction -> headerStateHolder.executeAction(action.action)

            SampleAction.CardsClick ->
                Log.d("SampleViewModel", "navigate to cards")

            SampleAction.TotalAssetsClick ->
                Log.d("SampleViewModel", "navigate to total assets")
        }
    }

    init {
        fetchTransactions()
        fetchWidgets()
    }

    private fun fetchTransactions() {
        val transactions = sampleRepository.getTransactions()
            .take(TRANSACTIONS_COUNT)

        _state.update {
            it.copy(transactions = transactions)
        }
    }

    private fun fetchWidgets() {
        val widgets = sampleRepository.getWidgets()

        _state.update {
            it.copy(widgets = widgets)
        }
    }
}

