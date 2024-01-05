package com.puzzle_agency.androidknowledge.knowledge.ui.view.video_player

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

/**
 * ExoPlayer variable uses composable context and will be destroyed after configuration change
 * You should create ExoPlayer and manage it in ViewModel
 * If using Hilt -> Scope the player to the ViewModel & use @ApplicationContext
 * !!! | don't forget to release the player | !!!
 */
@Composable
fun VideoPlayer(modifier: Modifier = Modifier, url: String) {
    val context = LocalContext.current

    val exoPlayer = remember {
        val mediaItem = MediaItem.fromUri(Uri.parse(url))
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItem)
            prepare()
        }
    }

    DisposableEffect(
        AndroidView(modifier = modifier, factory = {
            PlayerView(context).apply {
                this.player = exoPlayer
                useController = true
            }
        })
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}
