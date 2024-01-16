package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.widget

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.puzzle_agency.androidknowledge.R
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.SampleContentView
import com.puzzle_agency.androidknowledge.knowledge.util.NumberFormatter
import com.puzzle_agency.androidknowledge.ui.theme.NunitoFontFamily

object SampleWidgetView {

    private const val CARD_ICON_SIZE_DP = 35

    @Composable
    fun Compose(widget: SampleViewWidget) {
        when (widget) {
            is SampleViewWidget.Cards -> CardsWidget(widget = widget)
            is SampleViewWidget.TotalAssets -> TotalAssetsWidget(widget = widget)
        }
    }

    @Composable
    private fun TotalAssetsWidget(widget: SampleViewWidget.TotalAssets) {
        WidgetContainer(modifier = Modifier.offset(y = SampleContentView.ITEM_OFFSET_DP.dp)) {
            WidgetTitleButton(title = stringResource(id = R.string.total_assets)) {}

            val balanceSplitByDot = NumberFormatter.formatAsPrice(widget.totalBalance).split(",")
            val eurInt = balanceSplitByDot.getOrNull(0) ?: "0"
            val centsInts = balanceSplitByDot.getOrNull(1) ?: "0.0"

            val balanceTextStyle = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = NunitoFontFamily,
                fontWeight = FontWeight.Bold,
                lineHeight = 18.sp
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        balanceTextStyle.copy(fontSize = 24.sp, lineHeight = 56.sp).toSpanStyle()
                    ) {
                        append(eurInt)
                    }

                    withStyle(balanceTextStyle.toSpanStyle()) {
                        append(",$centsInts €")
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TotalAssetsWidgetItemType.entries.forEach { type ->
                    val balance = when (type) {
                        TotalAssetsWidgetItemType.Cash -> widget.cashBalance
                        TotalAssetsWidgetItemType.Investing -> widget.investingBalance
                        TotalAssetsWidgetItemType.ConnectedAccounts -> widget.connectedAccountsBalance
                        TotalAssetsWidgetItemType.Savings -> widget.savingsBalance
                    }

                    TotalAssetsListItem(type = type, balance)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }

    @Composable
    private fun TotalAssetsListItem(type: TotalAssetsWidgetItemType, balance: Double?) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(type.iconBackgroundColor, shape = CircleShape)
            ) {
                Icon(
                    imageVector = type.imageVector,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Text(
                text = stringResource(id = type.labelRes),
                color = Color.White,
                fontFamily = NunitoFontFamily,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )

            if (balance == null) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(color = 0xFF636666)
                )
            } else {
                Text(
                    text = "${NumberFormatter.formatAsPrice(balance)} €",
                    color = Color.White,
                    fontFamily = NunitoFontFamily,
                    fontSize = 12.sp
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun CardsWidget(widget: SampleViewWidget.Cards) {
        WidgetContainer(modifier = Modifier.offset(y = SampleContentView.ITEM_OFFSET_DP.dp)) {
            WidgetTitleButton(title = stringResource(id = R.string.cards)) {}

            val totalPages = 2

            val pagerState = rememberPagerState { totalPages }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) { index ->
                when (index) {
                    0 -> CardWidgetCards(widget.generalCardNumber, widget.virtualCardNumber)
                    else -> GetCardsPage()
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            PagerDotsIndicator(
                currentPageIndex = pagerState.currentPage,
                totalPages = totalPages,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

    @Composable
    private fun PagerDotsIndicator(
        modifier: Modifier = Modifier,
        currentPageIndex: Int,
        totalPages: Int
    ) {
        Row(
            modifier = modifier
                .padding(bottom = 8.dp)
                .height(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (i in 0 until totalPages) {
                val isSelected = currentPageIndex == i
                val size by animateDpAsState(
                    targetValue = if (isSelected) 8.dp else 4.dp,
                    label = "size_animation"
                )

                Box(
                    modifier = Modifier
                        .size(size)
                        .background(Color(color = 0xFF636666), shape = CircleShape)
                )
            }
        }
    }

    @Composable
    private fun GetCardsPage() {
        val color = Color(0xFF008cb4)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(height = CARD_ICON_SIZE_DP.dp, width = 55.dp)
                        .background(
                            color = color.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.get_card),
                    color = color,
                    fontFamily = NunitoFontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    @Composable
    private fun CardWidgetCards(generalCardNumber: String, virtualCardNumber: String) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            CardWidgetCardType.entries.forEach { type ->
                val cardNumber = when (type) {
                    CardWidgetCardType.Disposable -> null
                    CardWidgetCardType.General -> generalCardNumber
                    CardWidgetCardType.Virtual -> virtualCardNumber
                }

                CardWidgetCard(modifier = Modifier.weight(1f), type = type, cardNumber = cardNumber)
            }
        }
    }

    @Composable
    private fun CardWidgetCard(
        modifier: Modifier = Modifier,
        type: CardWidgetCardType,
        cardNumber: String?
    ) {
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.CreditCard,
                contentDescription = null,
                tint = type.cardColor,
                modifier = Modifier.size(CARD_ICON_SIZE_DP.dp),
            )

            Text(
                text = stringResource(id = type.labelRes),
                color = Color.White,
                fontFamily = NunitoFontFamily,
                fontSize = 14.sp
            )

            cardNumber?.let {
                Text(
                    text = it,
                    color = Color(color = 0xFF575b5a),
                    fontFamily = NunitoFontFamily,
                    fontSize = 12.sp
                )
            }
        }
    }

    @Composable
    private fun WidgetTitleButton(title: String, onClick: () -> Unit) {
        TextButton(
            onClick = onClick,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = title, color = Color(color = 0xFF636666), fontFamily = NunitoFontFamily)

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(color = 0xFF636666)
                )
            }
        }
    }

    @Composable
    private fun WidgetContainer(
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit
    ) {
        Card(
            modifier = modifier.padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(color = 0xFF131816)),
        ) {
            content()
        }
    }
}
