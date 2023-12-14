package com.puzzle_agency.androidknowledge.knowledge.network.network_connection_status

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkConnectionStatusManagerModule {

    @Provides
    @Singleton
    fun provideNetworkConnectionStatusManager(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope
    ): NetworkConnectionStatusManager = NetworkConnectionStatusManagerImpl(context, coroutineScope)
}