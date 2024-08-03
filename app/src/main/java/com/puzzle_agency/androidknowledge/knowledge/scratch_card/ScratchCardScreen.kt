package com.puzzle_agency.androidknowledge.knowledge.scratch_card

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.puzzle_agency.androidknowledge.knowledge.ui.modifier.clickable.clickableWithBounce
import kotlinx.coroutines.delay


@Composable
fun ScratchCardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CardScratch()
    }
}

@Preview
@Composable
private fun Preview() {
    ScratchCardScreen()
}

data class DraggedPath(
    val path: Path,
    val width: Float = 18f,
)

@Composable
private fun CardScratch(
    scratchBoxColor: Color = Color(0xFF5ACC80),
    scratchText: String = "Compose is \uD83D\uDD25",
) {
    val currentPathState = remember { mutableStateOf(DraggedPath(path = Path())) }
    val movedOffsetState = remember { mutableStateOf<Offset?>(null) }

    var canClick by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        delay(5_000)
        canClick = true
    }

    Column(
        modifier = Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF775BFD),
                    Color(0xFF60FACD)
                )
            )
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp),
            ) {
                val paint = Paint().asFrameworkPaint()
                Canvas(
                    modifier = Modifier
                        .clickableWithBounce(
                            pressedScale = if (canClick) .9f else 1f
                        ) {}
                        .align(alignment = Alignment.Center)
                        .size(200.dp)
                        .shadow(
                            elevation = 24.dp,
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                        .pointerInput(true) {
                            detectDragGestures { change, dragAmount ->
                                movedOffsetState.value = change.position
                            }
                        },
                ) {
                    paint.apply {
                        isAntiAlias = true
                        textSize = 100f
                        color = Color.Blue.toArgb()
                        textAlign = android.graphics.Paint.Align.CENTER
                    }

                    drawRoundRect(
                        color = scratchBoxColor,
                        size = Size(size.width, size.height),
                        colorFilter = ColorFilter.lighting(Color.LightGray, Color.DarkGray)
                    )

                    movedOffsetState.value?.let {
                        currentPathState.value.path.addOval(oval = Rect(it, 50f))
                    }

                    clipPath(path = currentPathState.value.path, clipOp = ClipOp.Intersect) {
                        drawRoundRect(color = Color.White, size = Size(size.width, size.height))
                        drawIntoCanvas {
                            it.nativeCanvas.drawText(
                                "\uD83C\uDF81",
                                center.x,
                                center.y + 40,
                                paint
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
