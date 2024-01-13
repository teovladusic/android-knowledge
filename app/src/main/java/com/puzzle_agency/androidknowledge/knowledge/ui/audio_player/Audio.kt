package com.puzzle_agency.androidknowledge.knowledge.ui.audio_player

import androidx.media3.common.MediaItem

data class Audio(
    val title: String,
    val subtitle: String,
    val audioUrl: String,
    val imageUrl: String?
)

fun MediaItem.toAudio() = Audio(
    title = mediaMetadata.title.toString(),
    subtitle = mediaMetadata.subtitle.toString(),
    audioUrl = mediaId,
    imageUrl = mediaMetadata.artworkUri.toString()
)
