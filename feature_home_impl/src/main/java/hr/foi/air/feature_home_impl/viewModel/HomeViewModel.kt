package hr.foi.air.feature_home_impl.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.foi.air.core.network.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class HomeViewModel: ViewModel() {
    private val repository = ApiRepository()

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun fetchMessage() {
        Log.d("HomeViewModel", "fetchMessage() called")

        viewModelScope.launch {
            try {
                val response = repository.getTestMessage()
                Log.d("HomeViewModel", "API response: $response")
                _message.value = response
            } catch (e: Exception) {
                Log.e("HomeViewModel", "API error: ${e.message}", e)
                _message.value = "Greška: ${e.message}"
            }
        }
    }
}
