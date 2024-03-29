package com.puzzle_agency.androidknowledge.knowledge.ui.view.animated_card

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.SafetyCheck
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.rounded.AddCard
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.puzzle_agency.androidknowledge.R
import com.puzzle_agency.androidknowledge.ui.theme.NunitoFontFamily
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

private val GrayBackground = Color(color = 0xFFf7f5ff)
private val PurpleColor = Color(color = 0xFF3b2873)

@Suppress("detekt.MagicNumber")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedCard() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Toolbar {} },
        bottomBar = { BottomBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(GrayBackground)
        ) {
            Title()

            Spacer(modifier = Modifier.height(36.dp))

            val pagerState = rememberPagerState(initialPage = 0) { 3 }

            VerticalPager(
                state = pagerState,
                modifier = Modifier
                    .height(220.dp)
                    .padding(horizontal = 16.dp),
                beyondBoundsPageCount = 100
            ) { page ->
                PagerPage(page = page, pagerState = pagerState)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "HOLD TO PAY",
                color = Color(color = 0xFFc0bec9),
                fontSize = 12.sp,
                letterSpacing = -(0.1).sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            CryptoAccountsBox()
        }
    }
}

@Composable
private fun ColumnScope.CryptoAccountsBox() {
    Row(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 32.dp, horizontal = 24.dp)
    ) {
        Account1()

        Spacer(modifier = Modifier.width(24.dp))

        Account2()
    }
}

@Composable
private fun RowScope.Account1() {
    Column(
        modifier = Modifier
            .weight(1f)
            .background(color = Color(color = 0xFF3b2872), shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = "USD",
            modifier = Modifier
                .background(color = Color(color = 0xFF8c79a7), shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp),
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "CRYPTO PORTFOLIO",
            color = Color(color = 0xFF6d5f97),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-.05).sp
        )

        Row {
            Text(
                text = "$ ",
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.offset(y = (-3).dp)
            )

            Text(
                text = "32,405.79",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "LEAD",
            color = Color(color = 0xFF6d5f97),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-.05).sp
        )

        Image(
            painter = painterResource(id = R.drawable.ic_mastercard_logo),
            contentDescription = "card_logo",
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun RowScope.Account2() {
    Column(
        modifier = Modifier
            .weight(1f)
            .background(color = Color(color = 0xFFf5e2f3), shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = "USD",
            modifier = Modifier
                .background(color = Color(color = 0xFFb19ec2), shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp),
            color = Color(color = 0xFF523e78),
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "CRYPTO PORTFOLIO",
            color = Color(color = 0xFFd0bcd2),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-.05).sp
        )

        Row {
            Text(
                text = "$ ",
                fontSize = 11.sp,
                color = Color(color = 0xFF3e2a70),
                modifier = Modifier.offset(y = (-3).dp)
            )

            Text(
                text = "32,405.79",
                color = Color(color = 0xFF3e2a70),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "LEAD",
            color = Color(color = 0xFF6d5f97),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-.05).sp
        )

        Image(
            painter = painterResource(id = R.drawable.ic_mastercard_logo),
            contentDescription = "card_logo",
            modifier = Modifier.size(20.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PagerPage(page: Int, pagerState: PagerState) {
    VirtualCard(
        modifier = Modifier
            .zIndex(100f - page)
            .graphicsLayer {
                val pageOffset = pagerState.offsetForPage(page)
                val offScreenBottom = pageOffset < 0f

                if (!offScreenBottom) {
                    val deg = 120f

                    val rotation = -min(pageOffset.absoluteValue * -deg, 90f)
                    rotationX = rotation

                    val radians = Math.toRadians(abs(rotation).toDouble())
                    val heightAfterRotation = (sin(radians) * size.height).toFloat()

                    translationY =
                        (size.height - heightAfterRotation * 1.3f) * pageOffset.absoluteValue

                    val scale = 1f - (pageOffset) * .15f
                    scaleX = scale
                    scaleY = scale

                    alpha = if (abs(rotation) >= 90f) 0f else 1f
                } else {
                    val deg = 35f

                    val interpolated =
                        FastOutLinearInEasing.transform(pageOffset.absoluteValue)

                    rotationX = (deg * interpolated).coerceAtMost(deg)

                    translationY = size.height * pageOffset

                    alpha = if (pageOffset >= -1f) 1f - abs(pageOffset) else 0f
                }

                transformOrigin = TransformOrigin(
                    pivotFractionX = .5f, pivotFractionY = 1f
                )
            }
            .drawWithContent {
                drawContent()
            },
        money = "${page + 1}${page * 5 % 3},${Random.nextInt(100, 999)}.${Random.nextInt(10, 99)}"
    )
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction

// OFFSET ONLY FROM THE LEFT
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.startOffsetForPage(page: Int): Float {
    return offsetForPage(page).coerceAtLeast(0f)
}


@Composable
private fun VirtualCard(modifier: Modifier, money: String) {
    Card(
        modifier = modifier.padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(color = 0xFFeeebfd)),
        border = BorderStroke(width = 2.dp, color = Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
            draggedElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(all = 16.dp)) {
                Text(
                    text = "DEBIT CARD",
                    color = Color(color = 0xFF878494),
                    letterSpacing = (-.3).sp,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Text(
                    text = "USD ACCOUNT",
                    color = Color.Black,
                    letterSpacing = (-.5).sp,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }

            Box(
                modifier = Modifier
                    .drawBehind {
                        drawOval(
                            color = PurpleColor,
                            topLeft = Offset(x = -70f, y = -400f),
                            size = Size(width = 630f, height = 700f)
                        )

                        drawCircle(
                            color = Color.Yellow,
                            radius = 60f,
                            center = Offset(x = size.width - 30f, y = 180f)
                        )
                    }
                    .fillMaxWidth(fraction = .5f)
            )
        }

        Text(
            text = "**** **** **** 3456",
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 12.dp, end = 32.dp),
            color = PurpleColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "$",
                    color = Color.Black,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = money,
                    color = Color.Black,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.8).sp
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_mastercard_logo),
                contentDescription = null,
                modifier = Modifier.size(44.dp)
            )
        }
    }
}

@Composable
private fun Title() {
    Text(
        text = "Good evening,\nTeo",
        fontSize = 30.sp,
        color = Color.Black,
        fontWeight = FontWeight.Black,
        lineHeight = 32.sp,
        fontFamily = NunitoFontFamily,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(onNotificationsClick: () -> Unit) {
    TopAppBar(
        title = { },
        actions = {
            IconButton(onClick = onNotificationsClick) {
                Icon(
                    imageVector = Icons.Rounded.AddCard,
                    contentDescription = "notifications",
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = GrayBackground)
    )
}


@Composable
private fun BottomBar() {
    val items = listOf(
        Icons.Default.PhoneAndroid,
        Icons.Default.Wallet,
        Icons.Default.CurrencyBitcoin,
        Icons.Default.SafetyCheck,
        Icons.Default.AccountBalance
    )

    BottomAppBar(containerColor = Color.White, modifier = Modifier.height(56.dp)) {
        items.forEachIndexed { index, image ->
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Icon(
                    imageVector = image,
                    contentDescription = null,
                    tint = if (index == 0) Color(color = 0xFF6650db) else Color(color = 0xFF9c9c9c)
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (index == 0) {
                    Box(
                        modifier = Modifier
                            .width(26.dp)
                            .height(3.dp)
                            .background(Color(color = 0xFF6650db), shape = RoundedCornerShape(2.dp))
                    )
                }
            }
        }
    }
}
