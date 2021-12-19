package com.signaltekno.huluapp.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "auth")
@ViewModelScoped
class DatastoreRepository @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    val flowBoard: Flow<Boolean> = dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[boardKey] ?: false
        }

    suspend fun saveOnBoard() {
        dataStore.edit { settings ->
            settings[boardKey] = true
        }
    }

    companion object PreferencesKey{
        val boardKey = booleanPreferencesKey("_onBoard")
    }
}