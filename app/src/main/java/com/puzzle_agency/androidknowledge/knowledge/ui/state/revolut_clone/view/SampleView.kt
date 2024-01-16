package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.puzzle_agency.androidknowledge.knowledge.ui.base.BaseView
import com.puzzle_agency.androidknowledge.knowledge.ui.base.Executor
import com.puzzle_agency.androidknowledge.knowledge.ui.state.SampleAction
import com.puzzle_agency.androidknowledge.knowledge.ui.state.SampleUiState
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.header.SampleHeaderView
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.header.rememberHeaderState
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
                val backgroundColor by derivedStateOf {
                    Color(color = 0xFF2B0944).copy(
                        alpha = if (scrollValue > 100) 1f else scrollValue / 100f
                    )
                }

                val headerState = rememberHeaderState(
                    uiState = state.headerState,
                    backgroundColor = backgroundColor,
                    executeAction = { executor(SampleAction.ExecuteHeaderAction(it)) }
                )

                SampleHeaderView.Compose(headerState = headerState)
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
}
