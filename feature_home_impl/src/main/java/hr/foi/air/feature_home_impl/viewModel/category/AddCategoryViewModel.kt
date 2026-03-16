package hr.foi.air.feature_home_impl.viewModel.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.feature_home_api.CategoryRepository
import hr.foi.feature_home_api.model.CategoryRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _categoryAdded = MutableStateFlow(false)
    val categoryAdded: StateFlow<Boolean> = _categoryAdded.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun addCategory(name: String, description: String, isDefault: Boolean) {
        viewModelScope.launch {
            val n = name.trim()
            val d = description.trim()

            if (n.isBlank()) {
                _error.value = "Naziv je obavezan."
                return@launch
            }

            _isLoading.value = true

            runCatching {
                repository.addCategory(
                    CategoryRequest(
                        name = n,
                        description = d,
                        isDefault = isDefault
                    )
                )
            }.onSuccess { res ->
                if (res.success) {
                    _categoryAdded.value = true
                } else {
                    _error.value = res.message ?: "Dodavanje kategorije nije uspjelo."
                }
            }.onFailure { e ->
                _error.value = e.localizedMessage ?: "Greška pri dodavanju kategorije."
            }

            _isLoading.value = false
        }
    }

    fun resetState() {
        _categoryAdded.value = false
        _error.value = null
        _isLoading.value = false
    }

    fun clearError() {
        _error.value = null
    }
}