package com.puzzle_agency.androidknowledge.knowledge.slack_cath_up

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import com.puzzle_agency.androidknowledge.R
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun SlackCatchUpAnimationScreen(cards: List<Card>, removeFirstCard: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(color = 0xFF81448f),
        topBar = { Toolbar(left = cards.size) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Cards(cards, removeFirstCard)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .padding(horizontal = 40.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomButton(
                    onClick = { /*TODO*/ },
                    color = Color.White,
                    text = "Keep Unread",
                    textColor = Color.Black
                )

                Spacer(modifier = Modifier.width(16.dp))

                CustomButton(
                    onClick = { /*TODO*/ },
                    color = Color(color = 0xFF387f66),
                    text = "Mark as Read",
                    textColor = Color.White
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.Cards(cards: List<Card>, removeFirstCard: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .weight(1f)
    ) {
        cards.take(2).forEachIndexed { index, i ->
            key(i.id) {
                val rotationZ = remember { Animatable(0f) }
                val offsetX = remember { Animatable(0f) }
                val maxRotation = 15
                val progress = remember { Animatable(0f) }
                val offset by animateDpAsState(
                    targetValue = index * 12.dp,
                    label = "offset_anim",
                    animationSpec = tween(durationMillis = 300)
                )
                val paddingHorizontalAddition by animateDpAsState(
                    targetValue = index * 8.dp,
                    label = "padding_anim",
                    animationSpec = tween(durationMillis = 300)
                )
                //val alpha by animateFloatAsState(targetValue = if (index > 1) 0f else 1f)

                Box(
                    modifier = Modifier
                        .zIndex(zIndex = cards.size.toFloat() - index)
                        .graphicsLayer(
                            transformOrigin = TransformOrigin(
                                pivotFractionX = 0.5f,
                                pivotFractionY = 0.9f
                            ),
                            rotationZ = rotationZ.value,
                            translationX = offsetX.value,
                            //alpha = alpha
                        )
                        .offset(y = -offset)
                        .pointerInput(key1 = Unit) {
                            detectHorizontalDragGestures(
                                onDragEnd = {
                                    if (progress.value.absoluteValue > .70f) {
                                        removeFirstCard()
                                    } else {
                                        coroutineScope.launch {
                                            launch {
                                                rotationZ.animateTo(
                                                    0f,
                                                    animationSpec = spring(dampingRatio = DampingRatioMediumBouncy)
                                                )
                                            }
                                            launch {
                                                offsetX.animateTo(
                                                    0f,
                                                    animationSpec = spring(dampingRatio = DampingRatioMediumBouncy)
                                                )
                                            }
                                            launch {
                                                progress.animateTo(
                                                    0f,
                                                    animationSpec = spring(dampingRatio = DampingRatioNoBouncy)
                                                )
                                            }
                                        }
                                    }
                                }
                            ) { change, dragAmount ->
                                change.consume()
                                coroutineScope.launch {
                                    launch {
                                        rotationZ.animateTo(
                                            rotationZ.value + (dragAmount / 30f),
                                            animationSpec = tween(durationMillis = 1)
                                        )
                                    }
                                    launch {
                                        offsetX.animateTo(
                                            offsetX.value + (dragAmount / 2f),
                                            animationSpec = tween(durationMillis = 1)
                                        )
                                    }
                                    launch {
                                        progress.animateTo(
                                            (rotationZ.value / maxRotation).coerceIn(-1f, 1f),
                                            animationSpec = tween(durationMillis = 1)
                                        )
                                    }
                                }
                            }
                        }
                        .padding(horizontal = paddingHorizontalAddition)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp))
                        .background(i.color)
                ) {
                    if (progress.value > 0 || true) {
                        val alpha = if (progress.value > .15f) progress.value else 0f
                        val background = Color(0xFF458d77).copy(alpha = alpha)
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(background)
                        )
                        Column(
                            modifier = Modifier
                                .alpha(alpha)
                                .align(Alignment.CenterStart)
                                .padding(start = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DoneAll,
                                contentDescription = null,
                                tint = background,
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color.White, CircleShape)
                                    .padding(5.dp)
                                    .align(Alignment.Start)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Mark as\nRead",
                                color = Color.White,
                                modifier = Modifier,
                                textAlign = TextAlign.Start,
                                fontSize = 22.sp,
                                lineHeight = 22.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    if (progress.value < 0) {
                        val value = progress.value.absoluteValue
                        val alpha = if (value > .15f) value else 0f
                        val background = Color(0xFF477db1).copy(alpha = alpha)

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(background)
                        )
                        Column(
                            modifier = Modifier
                                .alpha(alpha)
                                .align(Alignment.CenterEnd)
                                .padding(end = 16.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_prompt_suggestion),
                                contentDescription = null,
                                tint = background,
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color.White, CircleShape)
                                    .padding(5.dp)
                                    .rotate(180f)
                                    .align(Alignment.End)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Keep\nUnread",
                                color = Color.White,
                                modifier = Modifier,
                                textAlign = TextAlign.End,
                                fontSize = 22.sp,
                                lineHeight = 22.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Text(
                        text = "#${i.number}",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .rotate(18f)
                            .offset(x = (-20).dp, y = (-20).dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Black,
                        fontSize = 140.sp,
                    )
                }
            }
        }
    }
}

@Composable
private fun Toolbar(left: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Text(
            text = "$left left",
            color = Color.White,
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center)
        )

        Text(
            text = "Undo",
            color = Color.White.copy(alpha = .6f),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun RowScope.CustomButton(
    onClick: () -> Unit,
    color: Color,
    text: String,
    textColor: Color
) {
    Button(
        modifier = Modifier
            .height(44.dp)
            .weight(1f)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 2.dp)
    ) {
        Text(text = text, color = textColor, fontSize = 17.sp)
    }
}

@Preview
@Composable
private fun Preview() {
    SlackCatchUpAnimationScreen(
        listOf(Card(Color.Red, "", 1)), {}
    )
}
