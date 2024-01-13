package com.puzzle_agency.androidknowledge.knowledge.ui.audio_player

interface AudioController {

    var mediaControllerCallback: ((MediaControllerCallbackData) -> Unit)?
    fun executeAction(action: AudioControllerAction)
    fun getCurrentPosition(): Long
    fun getCurrentAudio(): Audio?
}

data class MediaControllerCallbackData(
    val audioPlayerState: AudioPlayerState,
    val currentAudio: Audio?,
    val currentPosition: Long,
    val totalDuration: Long,
    val isShuffleEnabled: Boolean,
    val isRepeatOneEnabled: Boolean
)

sealed interface AudioControllerAction {
    data class AddMediaItems(val audios: List<Audio>) : AudioControllerAction
    data class Play(val mediaItemIndex: Int) : AudioControllerAction
    data object Resume : AudioControllerAction
    data object Pause : AudioControllerAction
    data object Destroy : AudioControllerAction
    data object SkipToNextAudio : AudioControllerAction
    data object SkipToPreviousAudio : AudioControllerAction
    data class SeekTo(val position: Long) : AudioControllerAction
}
