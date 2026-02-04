package hr.foi.air.feature_home_impl.viewModel.auth


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.air.core.network.AuthApi
import hr.foi.air.core.network.data.RegisterData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.viewModelScope


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authApi: AuthApi
) : ViewModel() {
    private val _registerData = MutableStateFlow(RegisterData())
    val registerData = _registerData.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()


    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()


    fun onFieldChange(data: RegisterData) {
        _registerData.value = data
    }


    fun registerUser() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val result = authApi.register(_registerData.value)
                if (result.isSuccessful) {
                    _success.value = true
                } else {
                    _errorMessage.value = result.message() ?: "Greška u registraciji."
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Neuspješan zahtjev."
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun resetSuccess() {
        _success.value = false
    }
}
