package see.day.datastore

import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun hasToken(): Flow<Boolean>
    suspend fun clearData()
}
