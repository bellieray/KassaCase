package com.ebelli.kassacase.data.preferences

import kotlinx.coroutines.flow.Flow

interface Prefs {
    fun getSharedDouble(prefKey: String): Flow<Double>
    suspend fun setSharedDouble(prefKey: String, prefValue: Double)
}