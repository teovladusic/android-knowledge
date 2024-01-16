package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.widget

sealed interface SampleViewWidget {
    data class TotalAssets(
        val totalBalance: Double,
        val cashBalance: Double?,
        val investingBalance: Double?,
        val connectedAccountsBalance: Double?,
        val savingsBalance: Double?
    ) : SampleViewWidget

    data class Cards(
        val generalCardNumber: String,
        val virtualCardNumber: String
    ) : SampleViewWidget
}
