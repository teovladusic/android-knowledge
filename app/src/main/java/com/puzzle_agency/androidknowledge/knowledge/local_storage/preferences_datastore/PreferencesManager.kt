package com.puzzle_agency.androidknowledge.knowledge.local_storage.preferences_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.sampleDataStore: DataStore<Preferences> by preferencesDataStore("sample_preferences")

class PreferencesManagerImpl(@ApplicationContext private val context: Context) :
    PreferencesManager {

    override suspend fun setSessionToken(token: String?) {
        context.sampleDataStore.edit { preferences ->
            if (token == null) {
                preferences.remove(SamplePreferencesKey.SESSION_TOKEN)
            } else {
                preferences[SamplePreferencesKey.SESSION_TOKEN] = token
            }
        }
    }

    override val samplePreferencesFlow: Flow<SamplePreferences>
        get() = context.sampleDataStore.data.map { preferences ->
            mapSamplePreferences(preferences)
        }

    private fun mapSamplePreferences(preferences: Preferences): SamplePreferences {
        val token = preferences[SamplePreferencesKey.SESSION_TOKEN]
        return SamplePreferences(token)
    }
}

interface PreferencesManager {

    val samplePreferencesFlow: Flow<SamplePreferences>
    suspend fun setSessionToken(token: String?)
}
