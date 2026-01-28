package hr.foi.air.core.network.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    suspend fun setIsLoggedIn(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = value
        }
    }
}
