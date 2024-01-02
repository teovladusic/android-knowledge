package com.puzzle_agency.androidknowledge.knowledge.ui.modifier.clickable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.clickableWithBackground(
    idleBackground: Color,
    pressedBackground: Color,
    onClick: () -> Unit
): Modifier = composed {
    var clickState by rememberSaveable {
        mutableStateOf(ClickState.Idle)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val backgroundColor by animateColorAsState(
        targetValue = when (clickState) {
            ClickState.Pressed -> pressedBackground
            ClickState.Idle -> idleBackground
        },
        label = "ColorAnimation"
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
        .background(backgroundColor)
}
