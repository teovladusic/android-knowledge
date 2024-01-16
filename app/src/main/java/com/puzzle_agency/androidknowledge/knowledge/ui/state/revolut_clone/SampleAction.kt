package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone

import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.state_holder.SampleHeaderAction
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.parallax.ParallaxViewActionButtonType

sealed interface SampleAction {
    data class ExecuteHeaderAction(val action: SampleHeaderAction) : SampleAction
    data object AccountsButtonClick : SampleAction
    data class ParallaxActionButtonClick(val type: ParallaxViewActionButtonType) : SampleAction
    data class TransactionClick(val id: Int) : SampleAction
    data object SeeAllTransactionsClick : SampleAction
    data object AddWidgetClick : SampleAction
    data object TotalAssetsClick : SampleAction
    data object CardsClick : SampleAction
}