package com.puzzle_agency.androidknowledge.knowledge.ui.modifier

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

fun Modifier.shimmerBackground(
    shape: Shape = RectangleShape,
    shimmerColors: List<Color> = defaultColors,
    durationMillis: Int = 2000
): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    val transition = rememberInfiniteTransition(label = "infinite transition")
    val startOffsetX by transition.animateFloat(
        initialValue = -2f * size.width.toFloat(),
        targetValue = 2f * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis)
        ),
        label = "float"
    )

    background(
        brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        ),
        shape = shape
    ).onGloballyPositioned {
        size = it.size
    }
}

private val defaultColors = listOf(
    Color.Gray.copy(alpha = 0.9f),
    Color.Gray.copy(alpha = 0.5f),
    Color.Gray.copy(alpha = 0.9f)
)
