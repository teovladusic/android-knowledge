package com.puzzle_agency.androidknowledge.knowledge.ui.audio_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puzzle_agency.androidknowledge.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioPlayerViewModel @Inject constructor(
    private val audioController: AudioController
) : ViewModel() {

    companion object {
        private const val CURRENT_PLAYER_POSITION_UPDATE_DELAY_MS = 50L
    }

    private val _state = MutableStateFlow(AudioPlayerUiState())
    val state = _state.asStateFlow()

    init {
        setMediaControllerCallback()
    }

    private fun setMediaControllerCallback() {
        audioController.mediaControllerCallback = { data ->
            with(data) {
                audioPlayerState

                _state.update {
                    it.copy(
                        controllerState = it.controllerState.copy(
                            playerState = audioPlayerState,
                            currentAudio = currentAudio,
                            currentPosition = currentPosition,
                            totalDuration = totalDuration,
                            isShuffleEnabled = isShuffleEnabled,
                            isRepeatOneEnabled = isRepeatOneEnabled
                        ),
                    )
                }

                if (audioPlayerState == AudioPlayerState.PLAYING) {
                    viewModelScope.launch {
                        while (true) {
                            delay(CURRENT_PLAYER_POSITION_UPDATE_DELAY_MS)

                            _state.update {
                                it.copy(
                                    controllerState = it.controllerState.copy(
                                        currentPosition = audioController.getCurrentPosition()
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun executeAction(action: AudioPlayerAction) {
        when (action) {
            AudioPlayerAction.PlayPauseClick -> onPlayPauseClick()

            AudioPlayerAction.NextAudioClick ->
                audioController.executeAction(AudioControllerAction.SkipToNextAudio)

            AudioPlayerAction.PreviousAudioClick ->
                audioController.executeAction(AudioControllerAction.SkipToPreviousAudio)

            is AudioPlayerAction.SliderValueChanged -> _state.update {
                it.copy(sliderValue = action.value.toLong())
            }

            AudioPlayerAction.SliderValueReleased -> {
                val value = _state.value.sliderValue ?: 0L
                audioController.executeAction(AudioControllerAction.SeekTo(value))
                _state.update {
                    it.copy(sliderValue = null)
                }
            }
        }
    }

    private fun onPlayPauseClick() {
        val currentAudio = audioController.getCurrentAudio()

        if (currentAudio == null) {
            audioController.executeAction(AudioControllerAction.AddMediaItems(listOf(getDefaultAudio())))
        }

        when (_state.value.controllerState.playerState) {
            AudioPlayerState.PLAYING -> audioController.executeAction(AudioControllerAction.Pause)

            AudioPlayerState.PAUSED,
            AudioPlayerState.STOPPED -> audioController.executeAction(AudioControllerAction.Resume)

            null -> audioController.executeAction(AudioControllerAction.Play(0))
        }
    }

    private fun getDefaultAudio(): Audio {
        return Audio(
            title = "Counting",
            subtitle = "Android Knowledge",
            audioUrl = "android.resource://com.puzzle_agency.androidknowledge/${R.raw.counting}",
            imageUrl = "android.resource://com.puzzle_agency.androidknowledge/${R.drawable.geometry}"
        )
    }

    override fun onCleared() {
        audioController.executeAction(AudioControllerAction.Destroy)
        super.onCleared()
    }
}

data class AudioPlayerUiState(
    val controllerState: AudioControllerUiState = AudioControllerUiState(),
    val sliderValue: Long? = null
)

data class AudioControllerUiState(
    val playerState: AudioPlayerState? = null,
    val currentAudio: Audio? = null,
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val isShuffleEnabled: Boolean = false,
    val isRepeatOneEnabled: Boolean = false
)

sealed interface AudioPlayerAction {
    data object PlayPauseClick : AudioPlayerAction
    data object NextAudioClick : AudioPlayerAction
    data object PreviousAudioClick : AudioPlayerAction
    data object SliderValueReleased : AudioPlayerAction
    data class SliderValueChanged(val value: Float) : AudioPlayerAction
}
