package com.puzzle_agency.androidknowledge.knowledge.ui.modifier.clickable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput

/**
 * Scales the container on which you apply the modifier.
 * Should be used before any size modifiers.
 */
fun Modifier.clickableWithBounce(pressedScale: Float = 0.9f, onClick: () -> Unit) = composed {
    var clickState by rememberSaveable {
        mutableStateOf(ClickState.Idle)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val scale by animateFloatAsState(
        targetValue = if (clickState == ClickState.Pressed) pressedScale else 1f,
        label = "scale_animation"
    )

    clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = onClick
    )
        .pointerInput(clickState) {
            awaitPointerEventScope {
                clickState = if (clickState == ClickState.Pressed) {
                    waitForUpOrCancellation()
                    ClickState.Idle
                } else {
                    awaitFirstDown(false)
                    ClickState.Pressed
                }
            }
        }
        .scale(scale)
}
