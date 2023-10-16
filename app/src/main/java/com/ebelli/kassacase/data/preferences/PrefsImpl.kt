package com.ebelli.kassacase.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val PREFERENCES_NAME = "preferences"

class PrefsImpl(private val context: Context) : Prefs {
    private val Context.dataStore by preferencesDataStore(
        name = PREFERENCES_NAME
    )

    override fun getSharedDouble(prefKey: String): Flow<Double> =
        context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { it[doublePreferencesKey(prefKey)] ?: 0.0 }

    override suspend fun setSharedDouble(prefKey: String, prefValue: Double) {
        context.dataStore.edit {
            it[doublePreferencesKey(prefKey)] = prefValue
        }
    }
}