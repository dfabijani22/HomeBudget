package hr.foi.air.feature_home_impl.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    private val KEY_TOKEN = stringPreferencesKey("auth_token")

    val isLoggedIn: Flow<Boolean> = dataStore.data
        .map { it[KEY_IS_LOGGED_IN] ?: false }
        .distinctUntilChanged()

    val tokenFlow: Flow<String?> = dataStore.data
        .map { it[KEY_TOKEN] }
        .distinctUntilChanged()

    @Volatile private var cachedToken: String? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        scope.launch {
            tokenFlow.collect { cachedToken = it }
        }
    }

    fun currentToken(): String? = cachedToken

    suspend fun setIsLoggedIn(value: Boolean) {
        dataStore.edit { prefs -> prefs[KEY_IS_LOGGED_IN] = value }
    }

    suspend fun clearLoginState() {
        dataStore.edit { prefs ->
            prefs[KEY_IS_LOGGED_IN] = false
            prefs.remove(KEY_TOKEN)
        }
        cachedToken = null
    }

    suspend fun saveToken(token: String?) {
        dataStore.edit { prefs ->
            if (token.isNullOrBlank()) prefs.remove(KEY_TOKEN)
            else prefs[KEY_TOKEN] = token
        }
        cachedToken = token
    }
}