package com.puzzle_agency.androidknowledge.knowledge.ui.audio_player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.puzzle_agency.androidknowledge.knowledge.util.TimeConverter

object AudioPlayerView {

    @Suppress("detekt.MagicNumber")
    private val orangeColor = Color(0xFFff6144)

    @Suppress("detekt.MagicNumber")
    private val greyColor = Color(0xFFa69dbc)

    @Composable
    fun Compose(viewModel: AudioPlayerViewModel = hiltViewModel()) {
        val state by viewModel.state.collectAsStateWithLifecycle()

        val gradient = Brush.linearGradient(
            0.7f to Color(0xFF291164),
            1f to Color(0xFF6f2c90),
            start = Offset.Zero,
            end = Offset.Infinite
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { Toolbar() }
        ) { padding ->
            Column(
                modifier = Modifier
                    .background(gradient)
                    .padding(padding)
            ) {
                PlayerView(
                    state = state,
                    onPlayPauseClick = { viewModel.executeAction(AudioPlayerAction.PlayPauseClick) },
                    onSkipToNextClick = { viewModel.executeAction(AudioPlayerAction.NextAudioClick) },
                    onSkipToPreviousClick = { viewModel.executeAction(AudioPlayerAction.PreviousAudioClick) },
                    onPositionChanged = { viewModel.executeAction(AudioPlayerAction.SliderValueReleased) },
                    onSliderValueChange = {
                        viewModel.executeAction(AudioPlayerAction.SliderValueChanged(it))
                    }
                )
            }
        }
    }

    @Composable
    private fun PlayerView(
        state: AudioPlayerUiState,
        onPlayPauseClick: () -> Unit,
        onSkipToPreviousClick: () -> Unit,
        onSkipToNextClick: () -> Unit,
        onPositionChanged: () -> Unit,
        onSliderValueChange: (Float) -> Unit,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(44.dp)
        ) {
            AsyncImage(
                model = state.controllerState.currentAudio?.imageUrl?.toUri(),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(24.dp))
                    .size(240.dp),
                contentScale = ContentScale.Crop,
            )

            Column {
                AudioInfo(
                    title = state.controllerState.currentAudio?.title ?: "",
                    artist = state.controllerState.currentAudio?.subtitle ?: "",
                    totalDuration = state.controllerState.totalDuration,
                    currentPosition = state.sliderValue ?: state.controllerState.currentPosition,
                    onPositionChanged = onPositionChanged,
                    onSliderValueChange = onSliderValueChange
                )

                Spacer(modifier = Modifier.height(44.dp))

                PlayerControls(
                    onPlayPauseClick = onPlayPauseClick,
                    onSkipToNextClick = onSkipToNextClick,
                    onSkipToPreviousClick = onSkipToPreviousClick,
                    isPlayingAudio = state.controllerState.playerState == AudioPlayerState.PLAYING
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Toolbar() {
        CenterAlignedTopAppBar(
            title = { Text(text = "Now playing", fontSize = 17.sp, fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent
            ),
            navigationIcon = {
                IconButton(onClick = { println("navigate back") }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        tint = greyColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
        )
    }

    @Composable
    private fun AudioInfo(
        title: String,
        artist: String,
        totalDuration: Long,
        currentPosition: Long,
        onPositionChanged: () -> Unit,
        onSliderValueChange: (Float) -> Unit,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 28.sp, fontWeight = FontWeight.Bold)

            Text(text = "by $artist", fontSize = 14.sp, color = greyColor)

            Spacer(modifier = Modifier.height(12.dp))

            AudioSlider(
                currentMillisecond = currentPosition,
                totalMilliseconds = totalDuration,
                onSliderValueChange = onSliderValueChange,
                onPositionChanged = onPositionChanged
            )
        }
    }

    @Composable
    private fun AudioSlider(
        currentMillisecond: Long,
        totalMilliseconds: Long,
        onSliderValueChange: (Float) -> Unit,
        onPositionChanged: () -> Unit,
    ) {
        var sliderState by remember {
            mutableStateOf(PlayerSliderState.Initial)
        }

        LaunchedEffect(key1 = sliderState) {
            if (sliderState == PlayerSliderState.DragFinished) {
                onPositionChanged()
                sliderState = PlayerSliderState.Initial
            }
        }

        Column {
            Slider(
                value = currentMillisecond.toFloat(),
                onValueChange = {
                    sliderState = PlayerSliderState.Dragging
                    onSliderValueChange(it)
                },
                onValueChangeFinished = {
                    sliderState = PlayerSliderState.DragFinished
                },
                valueRange = 0f..totalMilliseconds.toFloat(),
                colors = SliderDefaults.colors(
                    activeTrackColor = orangeColor,
                    thumbColor = orangeColor,
                    inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                ),
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-10).dp)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = TimeConverter.formatMillis(currentMillisecond.toDouble()),
                    fontSize = 11.sp,
                    color = Color.White
                )

                Text(
                    text = TimeConverter.formatMillis(totalMilliseconds.toDouble()),
                    fontSize = 11.sp,
                    color = Color.White
                )
            }
        }
    }

    @Composable
    private fun PlayerControls(
        onPlayPauseClick: () -> Unit,
        onSkipToPreviousClick: () -> Unit,
        onSkipToNextClick: () -> Unit,
        isPlayingAudio: Boolean
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = onSkipToPreviousClick) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            FilledIconButton(
                onClick = onPlayPauseClick,
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = orangeColor),
                modifier = Modifier.size(52.dp)
            ) {
                Icon(
                    imageVector = if (isPlayingAudio) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            IconButton(onClick = onSkipToNextClick) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
