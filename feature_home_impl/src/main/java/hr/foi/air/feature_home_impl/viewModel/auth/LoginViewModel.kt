package hr.foi.air.feature_home_impl.viewModel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.air.feature_home_impl.datastore.UserDataStore
import hr.foi.feature_home_api.api.AuthRepository
import hr.foi.feature_home_api.model.AuthRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val res = repo.login(AuthRequest(email, password))
            if (res.success) {
                val t = res.data?.token
                userDataStore.saveToken(t)
                userDataStore.setIsLoggedIn(true)
                _token.value = t
            } else {
                _error.value = res.message ?: "Prijava nije uspjela."
            }
        }
    }

    fun clearError() { _error.value = null }
}