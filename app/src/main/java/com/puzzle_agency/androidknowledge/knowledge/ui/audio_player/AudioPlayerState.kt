package com.puzzle_agency.androidknowledge.knowledge.ui.audio_player

import androidx.media3.common.Player

enum class AudioPlayerState {
    PLAYING,
    PAUSED,
    STOPPED;

    companion object {
        fun fromPlaybackState(playbackState: Int, isPlaying: Boolean): AudioPlayerState {
            return when (playbackState) {
                Player.STATE_IDLE -> STOPPED
                Player.STATE_ENDED -> STOPPED
                else -> if (isPlaying) PLAYING else PAUSED
            }
        }
    }
}
