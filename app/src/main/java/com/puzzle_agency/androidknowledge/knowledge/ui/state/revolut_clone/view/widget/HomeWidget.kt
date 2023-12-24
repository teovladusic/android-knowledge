package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.widget

sealed interface HomeWidget {
    data class TotalAssets(
        val totalBalance: Double,
        val cashBalance: Double?,
        val investingBalance: Double?,
        val connectedAccountsBalance: Double?,
        val savingsBalance: Double?
    ) : HomeWidget

    data class Cards(
        val generalCardNumber: String,
        val virtualCardNumber: String
    ) : HomeWidget
}