package com.puzzle_agency.androidknowledge.knowledge.ui.animation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HeartLikeAnimation(
    modifier: Modifier = Modifier,
    showAnimation: Boolean,
    tint: Color = Color.White
) {
    val animatedSize by animateDpAsState(
        targetValue = if (showAnimation) 40.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "like_animation"
    )

    Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = "like",
        modifier = modifier.size(animatedSize),
        tint = tint
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UseHeartLikeAnimation() {
    var isLiked by remember {
        mutableStateOf(false)
    }

    var showLikeAnimation by remember {
        mutableStateOf(false)
    }

    val coroutine = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .size(100.dp)
            .combinedClickable(
                onClick = {},
                onDoubleClick = {
                    isLiked = !isLiked

                    showLikeAnimation = true

                    coroutine.launch {
                        delay(1000)
                        showLikeAnimation = false
                    }
                }
            )
            .background(Color.Blue)
    ) {
        HeartLikeAnimation(
            showAnimation = showLikeAnimation,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
