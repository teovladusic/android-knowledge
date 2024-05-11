package com.puzzle_agency.androidknowledge.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.puzzle_agency.androidknowledge.ui.theme.AndroidKnowledgeTheme
import dagger.hilt.android.AndroidEntryPoint
import de.apuri.physicslayout.lib.BodyConfig
import de.apuri.physicslayout.lib.PhysicsLayout
import de.apuri.physicslayout.lib.drag.DragConfig
import de.apuri.physicslayout.lib.physicsBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(ChargeDetection(), intentFilter)

        setContent {
            AndroidKnowledgeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Component()
                }
            }
        }
    }
}

@Composable
fun Component() {
    val isCharging by ChargeManager.isCharging.collectAsStateWithLifecycle()

    Column {
        PhysicsLayout(modifier = Modifier.fillMaxSize()) {
            Numbers(isCharging = isCharging)

            var dragConfig by remember { mutableStateOf<DragConfig?>(null) }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp)
                    .clip(CircleShape)
            ) {
                Card(
                    modifier = Modifier
                        .physicsBody(
                            id = "container",
                            bodyConfig = BodyConfig(isStatic = dragConfig == null),
                            shape = CircleShape
                        )
                        .pointerInput(Unit) {
                            awaitPointerEventScope {
                                awaitFirstDown()
                                dragConfig = DragConfig()
                            }
                        }
                        .clip(CircleShape)
                ) {
                    val animationProgress = remember {
                        Animatable(0f)
                    }

                    LaunchedEffect(key1 = isCharging) {
                        if (!isCharging) {
                            animationProgress.animateTo(1f, tween(1500))
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .drawBehind {
                                clipRect(right = size.width * animationProgress.value) {
                                    drawCircle(
                                        color = Color.Green,
                                        style = Stroke(width = 3.dp.toPx())
                                    )
                                }
                            }
                    ) {
                        val color by animateColorAsState(
                            targetValue = if (animationProgress.value > .8f) Color.Green else Color.White,
                            label = "color_animation"
                        )

                        val gradient = Brush.verticalGradient(
                            1 - animationProgress.value to color,
                            1f to Color.Green,
                            tileMode = TileMode.Decal
                        )

                        Icon(
                            imageVector = Icons.Default.ElectricBolt,
                            contentDescription = null,
                            modifier = Modifier
                                .graphicsLayer(alpha = 0.99f)
                                .drawWithCache {
                                    onDrawWithContent {
                                        drawContent()
                                        drawRect(gradient, blendMode = BlendMode.SrcAtop)
                                    }
                                }
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

object ChargeManager {
    val isCharging = MutableStateFlow(true)
}

class ChargeDetection : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent ?: return
        context ?: return

        val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
            status == BatteryManager.BATTERY_STATUS_FULL

        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            ChargeManager.isCharging.update { !isCharging }
        }
    }
}

@Composable
fun BoxScope.Numbers(isCharging: Boolean) {
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .offset(x = (-61).dp)
    ) {
        Text(
            text = "1",
            fontSize = 84.sp,
            lineHeight = 84.sp,
            letterSpacing = 0.sp,
            modifier = Modifier
                .physicsBody(
                    id = "1",
                    bodyConfig = BodyConfig(isStatic = isCharging)
                ),
            style = TextStyle(
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            )
        )
    }
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .offset(x = (-30).dp)
    ) {
        Text(
            text = "3",
            fontSize = 84.sp,
            lineHeight = 84.sp,
            letterSpacing = 0.sp,
            modifier = Modifier
                .physicsBody(
                    id = "2",
                    bodyConfig = BodyConfig(isStatic = isCharging)
                ),
            style = TextStyle(
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            )
        )
    }
    Text(
        text = ".",
        fontSize = 84.sp,
        lineHeight = 84.sp,
        letterSpacing = 0.sp,
        modifier = Modifier
            .align(Alignment.Center)
            .physicsBody(
                id = "3",
                bodyConfig = BodyConfig(isStatic = isCharging),
            ),
        style = TextStyle(
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )
    )
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .offset(x = 18.dp)
    ) {
        Text(
            text = "1",
            fontSize = 84.sp,
            lineHeight = 84.sp,
            letterSpacing = 0.sp,
            modifier = Modifier
                .physicsBody(
                    id = "4",
                    bodyConfig = BodyConfig(isStatic = isCharging)
                ),
            style = TextStyle(
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            )
        )
    }
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .offset(x = 50.dp)
    ) {
        Text(
            text = "2",
            fontSize = 84.sp,
            lineHeight = 84.sp,
            letterSpacing = 0.sp,
            modifier = Modifier.physicsBody(
                id = "5",
                bodyConfig = BodyConfig(isStatic = isCharging)
            ),
            style = TextStyle(
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            )
        )
    }
}
