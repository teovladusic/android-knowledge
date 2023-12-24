package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.puzzle_agency.androidknowledge.knowledge.ui.state.SampleAction
import com.puzzle_agency.androidknowledge.knowledge.ui.state.SampleUiState
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.base.BaseView
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.base.Executor
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.state_holder.SampleHeaderAction
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.state_holder.SampleHeaderState
import kotlinx.coroutines.flow.Flow

/**
 * Revolut home page clone to demonstrate complex state handling
 */

object SampleView : BaseView<SampleUiState, Unit, SampleAction> {

    @Composable
    override fun Compose(
        state: SampleUiState,
        events: Flow<Unit>,
        executor: Executor<SampleAction>
    ) {
        var scrollValue by remember {
            mutableIntStateOf(0)
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Toolbar(
                    headerState = state.headerState,
                    executor = executor,
                    scrollValue = scrollValue
                )
            }
        ) { padding ->
            SampleContentView.Compose(
                modifier = Modifier
                    .padding(bottom = padding.calculateBottomPadding())
                    .fillMaxSize(),
                transactions = state.transactions,
                widgets = state.widgets,
                onScrollValueChange = { scrollValue = it },
                onAccountsClick = { executor(SampleAction.AccountsButtonClick) },
                onParallaxActionButtonClick = { executor(SampleAction.ParallaxActionButtonClick(it)) },
                onAddWidgetClick = { executor(SampleAction.AddWidgetClick) },
                onSeeAllTransactionsClick = { executor(SampleAction.SeeAllTransactionsClick) },
                onTransactionClick = { executor(SampleAction.TransactionClick(it)) }
            )
        }
    }

    @Composable
    private fun Toolbar(
        headerState: SampleHeaderState,
        executor: Executor<SampleAction>,
        scrollValue: Int
    ) {
        SampleHeaderView.Compose(
            query = headerState.searchQuery,
            userInitials = headerState.userInitials,
            onQueryChange = {
                val action = SampleHeaderAction.SearchQueryChanged(it)
                executor(SampleAction.ExecuteHeaderAction(action))
            },
            searchActive = headerState.isSearchActive,
            onSearchActiveChange = {
                val action = SampleHeaderAction.SearchActiveChanged(it)
                executor(SampleAction.ExecuteHeaderAction(action))
            },
            onUserInitialsClick = {
                val action = SampleHeaderAction.UserInitialsClick
                executor(SampleAction.ExecuteHeaderAction(action))
            },
            onStatsClick = {
                val action = SampleHeaderAction.StatsClick
                executor(SampleAction.ExecuteHeaderAction(action))
            },
            onCardsClick = {
                val action = SampleHeaderAction.CardsClick
                executor(SampleAction.ExecuteHeaderAction(action))
            },
            backgroundColor = Color(color = 0xFF2B0944).copy(
                alpha = if (scrollValue > 100) 1f else scrollValue / 100f
            ),
            searchResults = headerState.searchResults
        )
    }
}
