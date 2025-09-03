package see.day.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import see.day.datastore.DataStoreDataSource.PreferencesKey.ACCESS_TOKEN
import see.day.datastore.DataStoreDataSource.PreferencesKey.REFRESH_TOKEN

class DataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataSource {
    object PreferencesKey {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
    }

    override fun hasToken(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN] != null && preferences[REFRESH_TOKEN] != null
    }

    fun getAccessToken(): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN]
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN]
        }
    }

    suspend fun saveAccessToken(token: String) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = token
        }
    }

    suspend fun saveRefreshToken(token: String) {
        dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN] = token
        }
    }

    override suspend fun clearData() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
