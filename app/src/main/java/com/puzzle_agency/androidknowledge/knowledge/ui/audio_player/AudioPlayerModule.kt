package com.puzzle_agency.androidknowledge.knowledge.ui.audio_player

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AudioPlayerModule {

    @Provides
    @Singleton
    fun provideAudioAttributes(): AudioAttributes = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
        .setUsage(C.USAGE_MEDIA)
        .build()

    @Provides
    fun provideAudioController(@ApplicationContext context: Context): AudioController =
        AudioControllerImpl(context)
}
