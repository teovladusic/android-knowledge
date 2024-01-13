package com.puzzle_agency.androidknowledge.knowledge.ui.audio_player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.puzzle_agency.androidknowledge.knowledge.util.TimeConverter

object AudioPlayerView {
    @Composable
    fun Compose(viewModel: AudioPlayerViewModel = hiltViewModel()) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        PlayerView(
            state = state,
            onPlayPauseClick = { viewModel.executeAction(AudioPlayerAction.PlayPauseClick) },
            onSkipToNextClick = { viewModel.executeAction(AudioPlayerAction.NextAudioClick) },
            onSkipToPreviousClick = { viewModel.executeAction(AudioPlayerAction.PreviousAudioClick) },
            onPositionChanged = { viewModel.executeAction(AudioPlayerAction.SliderValueReleased) },
            onSliderValueChange = { viewModel.executeAction(AudioPlayerAction.SliderValueChanged(it)) }
        )
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
                .padding(vertical = 24.dp)
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Blue)
            )

            AudioInfo(
                title = "Counting",
                artist = "Android knowledge",
                totalDuration = state.controllerState.totalDuration,
                currentPosition = state.sliderValue ?: state.controllerState.currentPosition,
                onPositionChanged = onPositionChanged,
                onSliderValueChange = onSliderValueChange
            )

            PlayerControls(
                onPlayPauseClick = onPlayPauseClick,
                onSkipToNextClick = onSkipToNextClick,
                onSkipToPreviousClick = onSkipToPreviousClick,
                isPlayingAudio = state.controllerState.playerState == AudioPlayerState.PLAYING
            )
        }
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
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Text(text = artist)

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
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = TimeConverter.formatMillis(currentMillisecond.toDouble()),
                    fontSize = 12.sp
                )

                Text(
                    text = TimeConverter.formatMillis(totalMilliseconds.toDouble()),
                    fontSize = 12.sp
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
                Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = null)
            }

            Spacer(modifier = Modifier.width(16.dp))

            FilledIconButton(
                onClick = onPlayPauseClick,
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.White)
            ) {
                Icon(
                    imageVector = if (isPlayingAudio) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = onSkipToNextClick) {
                Icon(imageVector = Icons.Default.SkipNext, contentDescription = null)
            }
        }
    }
}
