package com.puzzle_agency.androidknowledge.knowledge.rate_your_experience

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun RateYourExperienceScreen() {
    var experience by remember { mutableStateOf(Experience.Good) }
    var color by remember { mutableStateOf(experience.color) }
    var mouthRotation by remember { mutableFloatStateOf(0f) }
    var darkColor by remember { mutableStateOf(experience.darkColor) }
    var eyeHeight by remember { mutableStateOf(88.dp) }
    var eyeWidth by remember { mutableStateOf(88.dp) }
    var eyeRotation by remember { mutableFloatStateOf(0f) }
    var sliderColor by remember { mutableStateOf(experience.sliderColor) }
    var badTextPosition by remember { mutableStateOf((-350).dp) }
    var notBadTextPosition by remember { mutableStateOf((-350).dp) }
    var goodTextPosition by remember { mutableStateOf((0).dp) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.height(100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Eye(
                    experience = Experience.Good,
                    color = darkColor,
                    eyeWidth,
                    eyeHeight,
                    -eyeRotation
                )
                Spacer(modifier = Modifier.width(16.dp))
                Eye(
                    experience = Experience.Good,
                    color = darkColor,
                    eyeWidth,
                    eyeHeight,
                    eyeRotation
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Mouth(mouthRotation, darkColor)
        }


        Spacer(modifier = Modifier.height(44.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text = Experience.Good.text,
                fontSize = 60.sp,
                letterSpacing = (-4.5).sp,
                fontWeight = FontWeight.Black,
                color = Color.Black.copy(alpha = .35f),
                modifier = Modifier.offset(x = goodTextPosition)
            )
            Text(
                text = Experience.Bad.text,
                fontSize = 60.sp,
                letterSpacing = (-4.5).sp,
                fontWeight = FontWeight.Black,
                color = Color.Black.copy(alpha = .35f),
                modifier = Modifier.offset(x = badTextPosition)
            )
            Text(
                text = Experience.NotBad.text,
                fontSize = 60.sp,
                letterSpacing = (-4.5).sp,
                fontWeight = FontWeight.Black,
                color = Color.Black.copy(alpha = .35f),
                modifier = Modifier.offset(x = notBadTextPosition)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        SliderExperience(
            experience = experience,
            darkColor = darkColor,
            sliderColor = sliderColor
        ) { percentage ->
            val targetColor: Color
            val targetRotation: Float
            val targetDarkColor: Color
            val targetSliderColor: Color
            when {
                percentage <= 0.5f -> {
                    targetColor = lerp(
                        Experience.Bad.color, Experience.NotBad.color, percentage / 0.5f
                    )
                    targetRotation = 180f
                    targetDarkColor = lerp(
                        Experience.Bad.darkColor, Experience.NotBad.darkColor, percentage / 0.5f
                    )
                    eyeHeight = lerp(
                        32.dp, 28.dp, percentage / .5f
                    )
                    eyeWidth = lerp(
                        32.dp, 88.dp, percentage / .5f
                    )
                    eyeRotation = androidx.compose.ui.util.lerp(
                        100f, 0f, percentage / .5f
                    )
                    targetSliderColor = lerp(
                        Experience.Bad.sliderColor, Experience.NotBad.sliderColor, percentage / 0.5f
                    )
                    badTextPosition = lerp(0.dp, 300.dp, percentage / 0.5f)
                }

                else -> {
                    targetColor = lerp(
                        Experience.NotBad.color,
                        Experience.Good.color,
                        (percentage - 0.5f) / 0.5f
                    )
                    targetRotation =
                        androidx.compose.ui.util.lerp(180f, 0f, (percentage - 0.5f) / 0.5f)
                    targetDarkColor = lerp(
                        Experience.NotBad.darkColor,
                        Experience.Good.darkColor,
                        (percentage - 0.5f) / 0.5f
                    )
                    eyeHeight = lerp(
                        28.dp, 88.dp, (percentage - 0.5f) / 0.5f
                    )
                    eyeWidth = lerp(
                        88.dp, 88.dp, (percentage - 0.5f) / 0.5f
                    )
                    eyeRotation = androidx.compose.ui.util.lerp(
                        0f, 0f, percentage / .5f
                    )
                    targetSliderColor = lerp(
                        Experience.NotBad.sliderColor,
                        Experience.Good.sliderColor,
                        (percentage - 0.5f) / 0.5f
                    )
                    badTextPosition = (-300).dp
                }
            }

            notBadTextPosition = when {
                percentage <= .5f -> {
                    lerp((-350).dp, 0.dp, percentage / 0.5f)
                }

                percentage <= .75f -> {
                    lerp(0.dp, 350.dp, (percentage - 0.5f) / 0.25f)
                }

                else -> (-350).dp
            }
            goodTextPosition = if (percentage > .75f)
                lerp((-300).dp, 0.dp, (percentage - 0.75f) / 0.25f)
            else (-300).dp

            color = lerp(color, targetColor, percentage)
            darkColor = lerp(darkColor, targetDarkColor, percentage)
            mouthRotation = androidx.compose.ui.util.lerp(mouthRotation, targetRotation, percentage)
            sliderColor = lerp(sliderColor, targetSliderColor, percentage)

            experience = when (percentage) {
                in .45f..(.55f) -> Experience.NotBad
                in 0f..(.44f) -> Experience.Bad
                else -> Experience.Good
            }
        }
    }
}

@Composable
private fun SliderExperience(
    experience: Experience,
    darkColor: Color,
    sliderColor: Color,
    onScroll: (Float) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val padding = 64 + 12 + 24
    val minOffset = 0f
    val maxOffset = screenWidth - padding
    val offsetInCenter = maxOffset / 2
    val dragDp = remember { Animatable(maxOffset.toFloat()) }

    Box(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.5.dp, end = 4.dp)
                .padding(horizontal = 6.dp)
                .fillMaxWidth()
                .background(sliderColor)
                .height(6.dp)
        )
        Text(
            text = "Bad",
            modifier = Modifier
                .offset(y = 30.dp, x = 4.dp)
                .align(Alignment.CenterStart)
        )
        Text(
            text = "Not bad",
            modifier = Modifier
                .offset(y = 30.dp)
                .align(Alignment.Center)
        )
        Text(
            text = "Good",
            modifier = Modifier
                .offset(x = (0).dp, y = 30.dp)
                .align(Alignment.CenterEnd)
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(sliderColor)
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(sliderColor)
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(sliderColor)
            )
        }
        Box(
            modifier = Modifier
                .offset(y = (-8).dp, x = dragDp.value.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            val offset = snapToClosestOffset(
                                dragDp.value,
                                minOffset,
                                offsetInCenter.toFloat(),
                                maxOffset.toFloat()
                            )
                            onScroll(offset / maxOffset)
                            coroutineScope.launch {
                                dragDp.animateTo(offset)
                            }
                        }
                    ) { change, dragAmount ->
                        change.consume()
                        val dp = dragAmount.toDp()
                        val newDrag =
                            (dragDp.targetValue.dp + dp).coerceIn(minOffset.dp, maxOffset.dp)

                        val scrolledPercentage = newDrag.value / maxOffset
                        onScroll(scrolledPercentage)

                        coroutineScope.launch {
                            dragDp.animateTo(
                                newDrag.value,
                                animationSpec = tween(durationMillis = 0)
                            )
                        }
                    }
                }
                .size(36.dp)
                .clip(CircleShape)
                .background(color = darkColor)
        )
    }
}

fun snapToClosestOffset(
    currentOffset: Float,
    minOffset: Float,
    centerOffset: Float,
    maxOffset: Float
): Float {
    val distances = mapOf(
        minOffset to abs(currentOffset - minOffset),
        centerOffset to abs(currentOffset - centerOffset),
        maxOffset to abs(currentOffset - maxOffset)
    )

    return distances.minByOrNull { it.value }?.key ?: currentOffset
}


@Composable
private fun Mouth(rotation: Float, darkColor: Color) {
    Canvas(
        modifier = Modifier.rotate(rotation),
        onDraw = {
            val path = Path().apply {
                moveTo(-50f, 0f) // Move to the starting point
                quadraticBezierTo(
                    000f,
                    50f,
                    50f,
                    000f
                ) // Create a quadratic bezier curve for the smile
            }

            drawPath(
                path = path,
                color = darkColor,
                style = Stroke(
                    width = 25f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    )
}

@Composable
private fun Eye(experience: Experience, color: Color, width: Dp, height: Dp, eyeRotation: Float) {
    val shape = when (experience) {
        Experience.Good -> CircleShape
        Experience.NotBad -> RoundedCornerShape(24.dp)
        Experience.Bad -> CircleShape
    }
    Box(
        modifier = Modifier
            .rotate(eyeRotation)
            .clip(shape)
            .size(width = width, height = height)
            .background(color)
    )
}

@Preview
@Composable
private fun Preview() {
    RateYourExperienceScreen()
}

val colorGood = Color(color = 0xFFA8C25C)
val colorNotBad = Color(color = 0xFFDFA141)
val colorBad = Color(color = 0xFFFF7759)

enum class Experience(val color: Color) {
    Good(colorGood),
    NotBad(colorNotBad),
    Bad(colorBad);

    val text: String
        @Composable get() = when (this) {
            Good -> "GOOD"
            NotBad -> "NOT BAD"
            Bad -> "BAD"
        }

    val darkColor: Color
        get() = when (this) {
            Good -> Color(color = 0xFF1D4000)
            NotBad -> Color(color = 0xFF4E2800)
            Bad -> Color(color = 0xFF830E01)
        }

    val sliderColor: Color
        get() = when (this) {
            Good -> Color(color = 0xFF9EB84B)
            NotBad -> Color(color = 0xFFD79721)
            Bad -> Color(color = 0xFFFA6E5A)
        }
}
