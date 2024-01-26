package com.puzzle_agency.androidknowledge.knowledge.ui.audio_player

import android.content.ComponentName
import android.content.Context
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

class AudioControllerImpl(context: Context) : AudioController {

    private var mediaControllerFuture: ListenableFuture<MediaController>
    private val mediaController: MediaController?
        get() = if (mediaControllerFuture.isDone) mediaControllerFuture.get() else null

    override var mediaControllerCallback: ((MediaControllerCallbackData) -> Unit)? = null

    init {
        val sessionToken =
            SessionToken(context, ComponentName(context, PlaybackService::class.java))
        mediaControllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        mediaControllerFuture.addListener({ controllerListener() }, MoreExecutors.directExecutor())
    }

    private fun controllerListener() {
        mediaController?.addListener(object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)

                with(player) {
                    mediaControllerCallback?.invoke(
                        MediaControllerCallbackData(
                            audioPlayerState = AudioPlayerState.fromPlaybackState(
                                playbackState,
                                isPlaying
                            ),
                            currentAudio = currentMediaItem?.toAudio(),
                            currentPosition = currentPosition.coerceAtLeast(0L),
                            totalDuration = duration.coerceAtLeast(0L),
                            isShuffleEnabled = shuffleModeEnabled,
                            isRepeatOneEnabled = repeatMode == Player.REPEAT_MODE_ONE
                        )
                    )
                }
            }
        })
    }

    override fun getCurrentPosition(): Long {
        return mediaController?.currentPosition ?: 0L
    }

    override fun getCurrentAudio(): Audio? {
        return mediaController?.currentMediaItem?.toAudio()
    }

    override fun executeAction(action: AudioControllerAction) {
        when (action) {
            is AudioControllerAction.AddMediaItems -> addMediaItems(action.audios)
            AudioControllerAction.Destroy -> destroy()
            AudioControllerAction.Pause -> mediaController?.pause()
            is AudioControllerAction.Play -> play(action.mediaItemIndex)
            AudioControllerAction.Resume -> mediaController?.play()
            is AudioControllerAction.SeekTo -> mediaController?.seekTo(action.position)
            AudioControllerAction.SkipToNextAudio -> mediaController?.seekToNext()
            AudioControllerAction.SkipToPreviousAudio -> mediaController?.seekToPrevious()
        }
    }

    private fun addMediaItems(audios: List<Audio>) {
        val mediaItems = audios.map {
            MediaItem.Builder()
                .setMediaId(it.audioUrl)
                .setUri(it.audioUrl)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(it.title)
                        .setSubtitle(it.subtitle)
                        .setArtist(it.subtitle)
                        .setArtworkUri(it.imageUrl?.toUri())
                        .build()
                ).build()
        }

        mediaController?.setMediaItems(mediaItems)
    }

    private fun destroy() {
        MediaController.releaseFuture(mediaControllerFuture)
        mediaControllerCallback = null
    }

    private fun play(mediaItemIndex: Int) {
        mediaController?.apply {
            seekToDefaultPosition(mediaItemIndex)
            playWhenReady = true
            prepare()
        }
    }
}
