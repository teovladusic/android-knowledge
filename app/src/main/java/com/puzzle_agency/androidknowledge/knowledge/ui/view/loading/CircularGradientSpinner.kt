package com.puzzle_agency.androidknowledge.knowledge.ui.view.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularGradientSpinner(
    modifier: Modifier = Modifier,
    spinnerSize: Dp = 100.dp,
    colors: List<Color> = defaultSpinnerColors,
    animationDuration: Int = 400
) {
    val infiniteTransition = rememberInfiniteTransition(label = "transition")

    val rotateAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDuration,
                easing = LinearEasing
            )
        ),
        label = "rotation"
    )

    CircularProgressIndicator(
        progress = { 1f },
        modifier = modifier
            .size(size = spinnerSize)
            .rotate(degrees = rotateAnimation)
            .border(
                width = 4.dp,
                brush = Brush.sweepGradient(colors),
                shape = CircleShape
            ),
        color = MaterialTheme.colorScheme.background,
        strokeWidth = 1.dp,
    )
}

private val defaultSpinnerColors = listOf(
    Color(0xFF5851D8),
    Color(0xFF833AB4),
    Color(0xFFC13584),
    Color(0xFFE1306C),
    Color(0xFFFD1D1D),
    Color(0xFFF56040),
    Color(0xFFF77737),
    Color(0xFFFCAF45),
    Color(0xFFFFDC80),
    Color(0xFF5851D8)
)