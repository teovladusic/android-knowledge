package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.puzzle_agency.androidknowledge.R
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.domain.Transaction
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.parallax.ParallaxView
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.parallax.ParallaxViewActionButtonType
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.widget.HomeWidget
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.widget.HomeWidgetView
import com.puzzle_agency.androidknowledge.knowledge.util.format
import com.puzzle_agency.androidknowledge.ui.theme.NunitoFontFamily

object SampleContentView {

    const val ITEM_OFFSET_DP = -20

    @Composable
    fun Compose(
        modifier: Modifier,
        transactions: List<Transaction>,
        widgets: List<HomeWidget>,
        onScrollValueChange: (Int) -> Unit,
        onAccountsClick: () -> Unit,
        onParallaxActionButtonClick: (ParallaxViewActionButtonType) -> Unit,
        onAddWidgetClick: () -> Unit,
        onSeeAllTransactionsClick: () -> Unit,
        onTransactionClick: (id: Int) -> Unit
    ) {
        val scrollState = rememberScrollState()

        LaunchedEffect(scrollState.value) {
            onScrollValueChange(scrollState.value)
        }

        Column(
            modifier = modifier
                .background(Color.Black)
                .verticalScroll(scrollState)
        ) {
            ParallaxView.Compose(
                scrollValue = scrollState.value,
                onAccountsClick = onAccountsClick,
                onActionButtonClick = onParallaxActionButtonClick
            )

            TransactionsCard(
                transactions = transactions,
                onSeeAllTransactionsClick = onSeeAllTransactionsClick,
                onTransactionClick = onTransactionClick
            )

            WidgetsTitle(onAddClick = onAddWidgetClick)

            val widgetsLastIndex = widgets.lastIndex
            widgets.forEachIndexed { index, widget ->
                HomeWidgetView.Compose(widget = widget)

                val isLast = index == widgetsLastIndex
                if (!isLast) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    @Composable
    private fun TransactionsCard(
        transactions: List<Transaction>,
        onSeeAllTransactionsClick: () -> Unit,
        onTransactionClick: (id: Int) -> Unit
    ) {
        Card(
            modifier = Modifier
                .offset(y = ITEM_OFFSET_DP.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(color = 0xFF131816))
        ) {
            transactions.forEach { transaction ->
                Transaction(transaction) { onTransactionClick(it) }
            }

            TextButton(
                onClick = onSeeAllTransactionsClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.see_all),
                    color = Color(color = 0xFF007da5)
                )
            }
        }
    }

    @Composable
    private fun Transaction(transaction: Transaction, onTransactionClick: (id: Int) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onTransactionClick(transaction.id) }
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (transaction.imageUrl.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.Cyan, shape = CircleShape)
                ) {
                    Text(
                        text = transaction.username[0].toString(),
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                AsyncImage(
                    model = transaction.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(text = transaction.username, color = Color.White)

                Text(
                    text = transaction.dateTime.format(),
                    color = Color(color = 0xFF5c615f),
                    fontSize = 12.sp
                )
            }

            Text(
                text = "${transaction.amount} €",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }

    @Composable
    private fun WidgetsTitle(onAddClick: () -> Unit) {
        Row(
            modifier = Modifier
                .offset(y = ITEM_OFFSET_DP.dp)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.widgets),
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = NunitoFontFamily,
                fontWeight = FontWeight.SemiBold
            )

            IconButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color(color = 0xFF0088af)
                )
            }
        }
    }
}
