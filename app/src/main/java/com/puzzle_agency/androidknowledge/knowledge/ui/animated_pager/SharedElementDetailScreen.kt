package com.puzzle_agency.androidknowledge.knowledge.ui.animated_pager

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.puzzle_agency.androidknowledge.ui.theme.NunitoFontFamily
import com.skydoves.orbital.OrbitalScope
import kotlinx.coroutines.delay

@Composable
fun OrbitalScope.SharedElementDetailScreen(
    sharedElement: @Composable OrbitalScope.() -> Unit,
    city: City,
    onBack: () -> Unit
) {
    BackHandler(true) { onBack() }

    Box(modifier = Modifier.fillMaxSize()) {
        sharedElement()

        var startAlphaAnimation by rememberSaveable {
            mutableStateOf(false)
        }

        LaunchedEffect(key1 = Unit) {
            delay(100)
            startAlphaAnimation = true
        }

        val alpha by animateFloatAsState(
            targetValue = if (startAlphaAnimation) 0.7f else 0f,
            label = "alphaAnimation"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = alpha))
                .zIndex(3f)
        )

        CityInfo(city)
    }
}

@Composable
private fun BoxScope.CityInfo(city: City) {
    Column(
        modifier = Modifier
            .align(Alignment.Center)
            .padding(horizontal = 16.dp)
            .zIndex(4f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var showElements by rememberSaveable {
            mutableStateOf(false)
        }

        LaunchedEffect(Unit) {
            delay(500)
            showElements = true
        }

        AnimatedVisibility(
            visible = showElements,
            enter = fadeIn()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.WbCloudy,
                    contentDescription = "weather",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = city.weatherText.uppercase(),
                    fontSize = 12.sp,
                    fontFamily = NunitoFontFamily
                )
            }
        }

        AnimatedVisibility(
            visible = showElements,
            enter = fadeIn(animationSpec = tween(delayMillis = 500)) + slideInVertically()
        ) {
            Text(
                text = city.name,
                fontSize = 38.sp,
                fontFamily = NunitoFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        AnimatedVisibility(
            visible = showElements,
            enter = fadeIn(
                animationSpec = tween(delayMillis = 1000)
            ) + slideInVertically(
                animationSpec = tween(delayMillis = 1000, durationMillis = 600),
                initialOffsetY = { it / 2 }
            )
        ) {
            Text(
                text = city.description,
                color = Color.White.copy(alpha = 0.7f),
                fontFamily = NunitoFontFamily,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
