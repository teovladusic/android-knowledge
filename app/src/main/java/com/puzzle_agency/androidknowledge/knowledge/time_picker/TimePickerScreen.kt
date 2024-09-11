package com.puzzle_agency.androidknowledge.knowledge.time_picker

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.puzzle_agency.androidknowledge.ui.theme.RobotoMonoFontFamily
import kotlinx.coroutines.delay

@Composable
fun TimePickerScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(color = 0xFF1D1D1D))
    ) {
        Card(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(
                containerColor = Color(color = 0xFF212121)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            shape = RoundedCornerShape(40.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "TIMER",
                    color = Color(0xFFC5C5C5),
                    fontSize = 14.sp,
                    fontFamily = RobotoMonoFontFamily
                )

                var hour by remember { mutableIntStateOf(0) }
                var min by remember { mutableIntStateOf(0) }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val style = MaterialTheme.typography.headlineLarge
                            .copy(color = Color(0xFFC5C5C5), fontFamily = RobotoMonoFontFamily)
                        AnimatedCounter(count = hour, style = style)
                        Text(text = ":", style = style)
                        AnimatedCounter(count = min, style = style)
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        SwipeClock { index ->
                            if (index == 1440) return@SwipeClock

                            val (h, m) = convertMinutesToHoursAndMinutes(minutes = index)
                            hour = h
                            min = m
                        }
                        MiddleLine()
                        Box(modifier = Modifier.fillMaxWidth(fraction = .7f)) {
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            listOf(Color(color = 0xFF212121), Color.Transparent)
                                        )
                                    )
                                    .align(Alignment.CenterStart)
                                    .height(36.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            listOf(Color.Transparent, Color(color = 0xFF212121))
                                        )
                                    )
                                    .align(Alignment.CenterEnd)
                                    .height(36.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MiddleLine() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(Color.Red, shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .offset(y = (-1).dp)
                .width(3.dp)
                .height(36.dp)
                .background(Color.Red)
        )
    }
}

private fun convertMinutesToHoursAndMinutes(minutes: Int): Pair<Int, Int> {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return Pair(hours, remainingMinutes)
}

@Composable
private fun SwipeClock(
    onMiddleIndex: (Int) -> Unit
) {
    val list = remember { (0..1440).toList() }
    val scrollState = rememberLazyListState()

    LaunchedEffect(key1 = Unit) {
        delay(200)
        scrollState.scrollToItem(index = list.lastIndex / 2)
    }

    val listInfo by remember { derivedStateOf { scrollState.layoutInfo.visibleItemsInfo } }

    LaunchedEffect(key1 = listInfo) {
        var first =
            scrollState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: return@LaunchedEffect
        println("teoteoeteo first prosa")
        var last =
            scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@LaunchedEffect

        if (first == 0) {
            last += (last - 6)
        }
        if (last == 1440) {
            first -= (1440 - first - 6)
        }

        var middle = (last - first) / 2

        if (first == 0) {
            middle -= 4
        }
        if (last == 1440) {
            middle += 4
        }

        println("teoteoeteo last prosa ${middle + first}")
        val middleItem = list.getOrNull(middle + first) ?: return@LaunchedEffect
        println("teoteoeteo middle prosa")
        println("teoteoteo first = $first, last = $last, middle = $middle, middleItem = $middleItem")
        onMiddleIndex(middleItem)
    }

    val background5 = Color(0xFFC5C5C5).copy(alpha = .5f)
    val backgroundOther = Color(color = 0xFF2D2D2D)

    LazyRow(
        state = scrollState,
        modifier = Modifier
            .fillMaxWidth(fraction = .7f)
            .height(32.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 70.dp, end = 70.dp, bottom = 1.5.dp)
    ) {
        items(list.size) { i ->
            val color = if (i % 5 == 0) background5 else backgroundOther
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(color)
            )
        }
    }
}

@Composable
private fun AnimatedCounter(
    count: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    var oldCount by remember { mutableIntStateOf(count) }
    SideEffect { oldCount = count }

    Row(modifier = modifier) {
        val countString = count.toString()
        val oldCountString = oldCount.toString()
        for (i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                countString[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                },
                label = "char_anim"
            ) {
                Text(
                    text = if (countString.length == 1) "0$it" else it.toString(),
                    style = style,
                    softWrap = false
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TimePickerScreen()
}
