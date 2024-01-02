package com.puzzle_agency.androidknowledge.knowledge.ui.modifier.drag_to_dismiss

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

fun Modifier.draggableToDismiss(
    screenSwipeThresholdDivisor: Int = 4,
    dismiss: () -> Unit
) = composed {
    var offsetY by remember { mutableIntStateOf(0) }

    val animatedOffset by animateIntAsState(
        targetValue = offsetY,
        label = "offset_animation"
    )

    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val screenHeightPx = with(LocalDensity.current) { screenHeightDp.toPx() }

    offset { IntOffset(x = 0, y = animatedOffset) }
        .pointerInput(Unit) {
            detectDragGestures(
                onDragEnd = {
                    if (offsetY > (screenHeightPx / screenSwipeThresholdDivisor)) {
                        dismiss()
                    } else {
                        offsetY = 0
                    }
                }
            ) { change, dragAmount ->
                change.consume()

                val newOffset = offsetY + dragAmount.y.toInt()
                if (newOffset >= 0) {
                    offsetY = newOffset
                }
            }
        }
}
