package see.day.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import see.day.datastore.DataStoreDataSource.PreferencesKey.ACCESS_TOKEN
import see.day.datastore.DataStoreDataSource.PreferencesKey.IS_FIRST_LAUNCH
import see.day.datastore.DataStoreDataSource.PreferencesKey.IS_SHOWN_TUTORIAL
import see.day.datastore.DataStoreDataSource.PreferencesKey.REFRESH_TOKEN
import see.day.datastore.DataStoreDataSource.PreferencesKey.TODAY_DATE

class DataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataSource {
    object PreferencesKey {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        val TODAY_DATE = stringPreferencesKey("TODAY_TOKEN")
        val IS_FIRST_LAUNCH = booleanPreferencesKey("FIRST_LAUNCH")
        val IS_SHOWN_TUTORIAL = booleanPreferencesKey("SHOWN_TUTORIAL")
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

    fun getTodayDate() : Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[TODAY_DATE]
        }
    }

    fun getFirstLaunchState() : Flow<Boolean?> {
        return dataStore.data.map { prefs ->
            prefs[IS_FIRST_LAUNCH]
        }
    }

    fun getIsShownTutorial() : Flow<Boolean?> {
        return dataStore.data.map { prefs ->
            prefs[IS_SHOWN_TUTORIAL]
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

    suspend fun saveTodayDate(date: String) {
        dataStore.edit { prefs ->
            prefs[TODAY_DATE] = date
        }
    }

    suspend fun setAppLaunch() {
        dataStore.edit { prefs ->
            prefs[IS_FIRST_LAUNCH] = false
        }
    }

    suspend fun setShownTutorial() {
        dataStore.edit { prefs ->
            prefs[IS_SHOWN_TUTORIAL] = true
        }
    }

    override suspend fun clearData() {
        dataStore.edit { prefs ->
            prefs.clear()
            prefs[IS_FIRST_LAUNCH] = false
            prefs[IS_SHOWN_TUTORIAL] = true
        }
    }
}
