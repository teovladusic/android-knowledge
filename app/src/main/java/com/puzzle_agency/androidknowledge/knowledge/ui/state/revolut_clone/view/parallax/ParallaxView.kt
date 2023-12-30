package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.parallax

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.puzzle_agency.androidknowledge.R
import com.puzzle_agency.androidknowledge.knowledge.util.NumberFormatter
import com.puzzle_agency.androidknowledge.ui.theme.NunitoFontFamily

object ParallaxView {

    private val buttonsBackgroundColor = Color(color = 0xFF647165).copy(alpha = 0.7f)
    private const val PARALLAX_IMAGE_SCREEN_HEIGHT_FRACTION = 0.6f
    private const val TRANSLATION_FRACTION = 0.2f

    @Composable
    fun Compose(
        scrollValue: Int,
        onAccountsClick: () -> Unit,
        onActionButtonClick: (ParallaxViewActionButtonType) -> Unit
    ) {
        val screenHeight = LocalConfiguration.current.screenHeightDp

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.parallax_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight * PARALLAX_IMAGE_SCREEN_HEIGHT_FRACTION).dp)
                    .graphicsLayer { translationY = TRANSLATION_FRACTION * scrollValue },
                contentScale = ContentScale.Crop
            )

            Accounts(
                balance = 0.54,
                modifier = Modifier.align(Alignment.Center),
                onAccountsClick = onAccountsClick
            )

            ActionButtons(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-40).dp),
                onClick = onActionButtonClick
            )
        }
    }

    @Composable
    private fun Accounts(
        modifier: Modifier = Modifier,
        balance: Double,
        onAccountsClick: () -> Unit
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val mainText = stringResource(id = R.string.main)
            val eurText = "EUR"

            Text(
                text = "$mainText \u2022 $eurText",
                color = Color.White,
                fontSize = 12.sp,
                fontFamily = NunitoFontFamily
            )

            val balanceSplitByDot = NumberFormatter.formatAsPrice(balance).split(",")
            val eurInt = balanceSplitByDot.getOrNull(0) ?: "0"
            val centsInts = balanceSplitByDot.getOrNull(1) ?: "0.0"

            val balanceTextStyle = TextStyle(
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = NunitoFontFamily,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        balanceTextStyle.copy(fontSize = 48.sp, lineHeight = 50.sp).toSpanStyle()
                    ) {
                        append(eurInt)
                    }

                    withStyle(balanceTextStyle.toSpanStyle()) {
                        append(",$centsInts €")
                    }
                },
            )

            FilledTonalButton(
                onClick = onAccountsClick,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = buttonsBackgroundColor
                )
            ) {
                Text(
                    text = stringResource(id = R.string.accounts),
                    color = Color.White,
                    fontSize = 13.sp,
                    fontFamily = NunitoFontFamily
                )
            }
        }
    }

    @Composable
    private fun ActionButtons(
        modifier: Modifier = Modifier,
        onClick: (ParallaxViewActionButtonType) -> Unit
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ParallaxViewActionButtonType.entries.forEach { actionButton ->
                ActionButton(type = actionButton) { onClick(actionButton) }
            }
        }
    }

    @Composable
    private fun ActionButton(type: ParallaxViewActionButtonType, onClick: () -> Unit) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            FilledIconButton(
                onClick = onClick,
                colors = IconButtonDefaults.iconButtonColors(containerColor = buttonsBackgroundColor),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = type.imageVector,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = type.labelRes),
                color = Color.White,
                fontSize = 13.sp,
                maxLines = 2,
                modifier = Modifier.requiredWidth(70.dp),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                fontFamily = NunitoFontFamily,
                lineHeight = 15.sp
            )
        }
    }
}
