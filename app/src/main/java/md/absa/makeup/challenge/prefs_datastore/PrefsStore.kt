package md.absa.makeup.challenge.prefs_datastore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    fun isNightMode(): Flow<Boolean>

    suspend fun toggleNightMode()

    fun isWelcomeScreenShown(): Flow<Boolean>

    suspend fun setWelcomeScreenShown(value: Boolean)
}
