package hr.foi.air.feature_home_impl.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _message = MutableStateFlow("Dobrodošao u aplikaciju!")
    val message: StateFlow<String> = _message

    fun refreshMessage() {
        _message.value = "Osvježeno u ${System.currentTimeMillis()}"
    }

}
