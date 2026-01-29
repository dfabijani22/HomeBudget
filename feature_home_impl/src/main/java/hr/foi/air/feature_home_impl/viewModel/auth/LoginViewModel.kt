package hr.foi.air.feature_home_impl.viewModel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.air.core.network.AuthApi
import hr.foi.air.core.network.data.LoginData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authApi: AuthApi
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    fun login(email: String, password: String) {
        _isLoading.value = true
        _errorMessage.value = null

        val loginData = LoginData(email = email, password = password)

        viewModelScope.launch {
            try {
                val response = authApi.login(loginData)
                if (response.isSuccessful && response.body()?.success == true) {
                    _success.value = true
                } else {
                    _errorMessage.value = response.body()?.message ?: "Uneseni su krivi podaci, pokušajte ponovno"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Greška: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetSuccess() {
        _success.value = false
    }
}
