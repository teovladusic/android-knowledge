package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.widget

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.StackedLineChart
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.puzzle_agency.androidknowledge.R

enum class TotalAssetsWidgetItemType(
    val iconBackgroundColor: Color,
    val imageVector: ImageVector,
    @StringRes val labelRes: Int
) {
    Cash(
        iconBackgroundColor = Color(color = 0xFF6e8aff),
        imageVector = Icons.Default.Payments,
        labelRes = R.string.cash
    ),
    Investing(
        iconBackgroundColor = Color(color = 0xFF00beff),
        imageVector = Icons.Default.StackedLineChart,
        labelRes = R.string.investing
    ),
    ConnectedAccounts(
        iconBackgroundColor = Color(color = 0xFF00e3e9),
        imageVector = Icons.Default.Link,
        labelRes = R.string.connected_accounts
    ),
    Savings(
        iconBackgroundColor = Color(color = 0xFFff783b),
        imageVector = Icons.Default.Savings,
        labelRes = R.string.savings
    )
}