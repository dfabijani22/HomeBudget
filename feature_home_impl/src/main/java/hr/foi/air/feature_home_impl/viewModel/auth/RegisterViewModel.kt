package hr.foi.air.feature_home_impl.viewModel.auth


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import hr.foi.feature_home_api.api.AuthRepository
import hr.foi.feature_home_api.model.RegisterRequest


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess = _registerSuccess.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            val req = RegisterRequest(
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                name = name,
                surname = surname
            )

            val res = repo.register(req)

            if (res.success) {
                _registerSuccess.value = true
            } else {
                _error.value = res.message ?: "Registracija nije uspjela."
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
