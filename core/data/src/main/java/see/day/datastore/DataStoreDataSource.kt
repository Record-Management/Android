package see.day.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import see.day.datastore.DataStoreDataSource.PreferencesKey.ACCESS_TOKEN
import see.day.datastore.DataStoreDataSource.PreferencesKey.APP_START_STATE
import see.day.datastore.DataStoreDataSource.PreferencesKey.REFRESH_TOKEN
import see.day.model.navigation.AppStartState

class DataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataSource {
    object PreferencesKey {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        val APP_START_STATE = stringPreferencesKey("APP_NAV_STATE")
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

    fun getAppStartState(): Flow<AppStartState> {
        return dataStore.data.map { prefs ->
            AppStartState.fromString(prefs[APP_START_STATE])
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

    suspend fun saveAppStartState(state: AppStartState) {
        dataStore.edit { prefs ->
            prefs[APP_START_STATE] = state.toString().uppercase()
        }
    }

    override suspend fun clearData() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
