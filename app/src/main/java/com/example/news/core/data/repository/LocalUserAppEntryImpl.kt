package com.example.news.core.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.news.core.domain.repository.LocalUserAppEntry
import com.example.news.core.util.Constants.APP_ENTRY
import com.example.news.core.util.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * `LocalUserAppEntryImpl` manages user preferences for the initial app entry.
 *
 * Purpose:
 * - Tracks whether the user has completed onboarding to determine if the onboarding
 *   screen should be displayed on first launch.
 *
 * Usage:
 * - This class should be used during app startup to check onboarding status and show
 *   the appropriate screen (onboarding or main content) accordingly.
 */

class LocalUserAppEntryImpl(private val context: Context) : LocalUserAppEntry {
    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[booleanPreferencesKey(APP_ENTRY)] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[booleanPreferencesKey(APP_ENTRY)] ?: false
    }
}

private val Context.dataStore by preferencesDataStore(name = USER_SETTINGS)