package com.example.shoppingapp.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val DATASTORE_COLOR = "colors"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_COLOR)

class DataStoreManager(val context: Context) {
    suspend fun saveStringPreference(value: String, key: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    fun getStringPreference(key: String, defValue: String) =
        context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: defValue
        }


    companion object {
        const val TITLE_COLOR = "title_color"
    }
}