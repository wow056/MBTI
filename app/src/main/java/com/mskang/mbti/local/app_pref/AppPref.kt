package com.mskang.mbti.local.app_pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPref @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_pref")

    private val keyAccessToken = stringPreferencesKey("access_token")


    val accessTokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[keyAccessToken]
        }

    suspend fun setAccessToken(accessToken: String) {
        context.dataStore.edit { settings ->
            settings[keyAccessToken] = accessToken
        }
    }
}