package com.puzzle_agency.androidknowledge.knowledge.swipeable_bottom_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.rounded.HomeMax
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.puzzle_agency.androidknowledge.ui.theme.NunitoFontFamily
import kotlin.math.roundToInt

@Composable
fun SwipeableBottomBarScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(color = 0xFFF7F7F8))
    ) {
        BottomBar(
            modifier = Modifier
                .navigationBarsPadding()
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun BottomBar(modifier: Modifier = Modifier) {
    var isSwiping by remember { mutableStateOf(false) }

    val widthFraction by animateFloatAsState(
        targetValue = if (isSwiping) .9f else 1f,
        label = "width_anim"
    )
    var selectedItem by remember { mutableStateOf(BottomBarItem.Home) }
    val offsetY by animateDpAsState(
        targetValue = if (isSwiping) 24.dp else 0.dp,
        label = "offset_anim"
    )
    val cornerRadius by animateDpAsState(
        targetValue = if (isSwiping) 54.dp else 0.dp,
        label = "corners_anim"
    )
    val horizontalPadding by animateDpAsState(
        targetValue = if (isSwiping) 16.dp else 0.dp,
        label = "padding_anim"
    )
    val verticalPadding by animateDpAsState(
        targetValue = if (isSwiping) 24.dp else 12.dp,
        label = "padding_anim"
    )

    var totalWidth by remember { mutableFloatStateOf(0f) }
    var percentageScrolled by remember { mutableFloatStateOf(.5f) }

    Column(
        modifier = modifier
            .offset(y = -offsetY)
            .fillMaxWidth(fraction = widthFraction)
            .pointerInput(key1 = Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = { isSwiping = false },
                    onDragStart = { isSwiping = true },
                    onHorizontalDrag = { change, amount ->
                        val draggedOffsetX = change.position.x

                        // Calculate the percentage scrolled (0.0f to 1.0f)
                        percentageScrolled = (draggedOffsetX / totalWidth).coerceIn(0f, 1f)

                        val index = (percentageScrolled * 5).roundToInt()
                        selectedItem = BottomBarItem.entries.getOrNull(index) ?: selectedItem
                    }
                )
            }
    ) {
        Row(
            modifier = Modifier
                .shadow(elevation = 50.dp, spotColor = Color.Black, shape = RoundedCornerShape(cornerRadius))
                .background(
                    color = Color(color = 0xFFF7F7F8),
                    shape = RoundedCornerShape(size = cornerRadius)
                )
                .padding(vertical = verticalPadding, horizontal = horizontalPadding)
                .onSizeChanged { totalWidth = it.width.toFloat() }) {
            BottomBarItem.entries.forEach {
                BottomBarItemContent(
                    item = it,
                    isSelected = selectedItem == it,
                    isSwiping = isSwiping
                )
            }
        }
    }
}

@Composable
private fun RowScope.BottomBarItemContent(
    item: BottomBarItem,
    isSelected: Boolean,
    isSwiping: Boolean
) {
    val color by animateColorAsState(
        targetValue = if (isSelected) Color(color = 0xFF0000FF) else Color(color = 0xFF888888),
        label = "color_anim"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isSwiping && isSelected) 1.4f else 1f,
        label = "scale_anim"
    )

    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = item.vector,
            contentDescription = null,
            tint = color,
            modifier = Modifier.scale(scale = iconScale)
        )
        AnimatedVisibility(visible = !isSwiping) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.label, color = color, fontFamily = NunitoFontFamily, fontSize = 13.sp)
        }
    }
}

enum class BottomBarItem(val vector: ImageVector, val label: String) {
    Home(vector = Icons.Rounded.HomeMax, label = "Home"),
    Games(vector = Icons.Default.Gamepad, label = "Games"),
    Apps(vector = Icons.Default.Apps, label = "Apps"),
    Arcade(vector = Icons.Default.VideogameAsset, label = "Arcade"),
    Search(vector = Icons.Default.Search, label = "Search")
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    SwipeableBottomBarScreen()
}
