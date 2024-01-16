package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.data

import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.domain.Transaction
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.widget.SampleViewWidget
import java.time.LocalDateTime
import javax.inject.Inject

class SampleRepository @Inject constructor() {

    fun getTransactions(): List<Transaction> {
        return transactions
    }

    @Suppress("detekt.MagicNumber")
    fun getWidgets(): List<SampleViewWidget> {
        return listOf(
            SampleViewWidget.TotalAssets(
                totalBalance = 50000.0,
                cashBalance = 10000.0,
                investingBalance = 30000.0,
                connectedAccountsBalance = 5000.0,
                savingsBalance = 2000.0
            ),
            SampleViewWidget.Cards(
                generalCardNumber = "*3456",
                virtualCardNumber = "*9012"
            )
        )
    }
}

@Suppress("detekt.MagicNumber")
private val transactions = listOf(
    Transaction(
        1,
        "https://raw.githubusercontent.com/Ashwinvalento/cartoon-avatar/master/lib/images/female/10.png",
        "User1",
        LocalDateTime.now(),
        100.0
    ),
    Transaction(
        2,
        "https://raw.githubusercontent.com/Ashwinvalento/cartoon-avatar/master/lib/images/male/45.png",
        "User2",
        LocalDateTime.now(),
        150.0
    ),
    Transaction(
        3,
        "https://raw.githubusercontent.com/Ashwinvalento/cartoon-avatar/master/lib/images/male/86.png",
        "User3",
        LocalDateTime.now(),
        200.0
    ),
    Transaction(4, null, "User4", LocalDateTime.now(), 50.0)
)
