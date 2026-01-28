package hr.foi.air.feature_home_impl.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.foi.air.core.network.ApiRepository
import hr.foi.air.core.network.data.RegisterData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.air.core.network.AuthApi
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val authApi: AuthApi
) : ViewModel() {
    private val repository = ApiRepository()

    private val _registerResult = MutableStateFlow<String?>(null)
    val registerResult: StateFlow<String?> = _registerResult

    fun registerUser(data: RegisterData) {
        viewModelScope.launch {
            try {
                val response = repository.registerUser(data)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.success == true) {
                        _registerResult.value = body.message
                    } else {
                        _registerResult.value = body?.message ?: "Registracija nije uspjela."
                    }
                } else {
                    _registerResult.value = "Greška: ${response.code()}"
                }
            } catch (e: Exception) {
                _registerResult.value = "Greška: ${e.localizedMessage ?: "Nepoznata greška"}"
            }
        }
    }
}
