package hr.foi.air.feature_home_impl.viewModel.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.air.core.network.CategoryApiService
import hr.foi.air.core.network.data.CategoryData
import hr.foi.air.core.network.data.CategoryRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateCategoryViewModel @Inject constructor(
    private val api: CategoryApiService
) : ViewModel() {

    private val _category = MutableStateFlow<CategoryData?>(null)
    val category: StateFlow<CategoryData?> = _category

    private val _updateSuccess = MutableStateFlow(false)
    val updateSuccess: StateFlow<Boolean> = _updateSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage
    private val _deleteSuccess = MutableStateFlow(false)
    val deleteSuccess: StateFlow<Boolean> = _deleteSuccess

    fun loadCategory(id: Int) {
        viewModelScope.launch {
            try {
                val response = api.getCategoryById(id)
                Log.d("UpdateDebug", "LOADING category id=$id")
                if (response.isSuccessful) {
                    _category.value = response.body()
                } else {
                    _errorMessage.value = "Greška pri učitavanju kategorije"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun updateCategory(id: Int, name: String, description: String) {
        viewModelScope.launch {
            try {
                val request = CategoryRequest(
                    name = name,
                    description = description
                )
                val response = api.updateCategory(id, request)

                if (response.isSuccessful && response.body()?.success == true) {
                    _updateSuccess.value = true
                } else {
                    _errorMessage.value = response.body()?.message ?: "Greška pri spremanju"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun clearMessages() {
        _errorMessage.value = null
        _updateSuccess.value = false
    }

    fun deleteCategory(id: Int) {
        viewModelScope.launch {
            try {
                val response = api.deleteCategory(id)
                if (response.isSuccessful) {
                    _deleteSuccess.value = true
                } else {
                    Log.e("DeleteCategoryVM", "Error deleting: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("DeleteCategoryVM", "Exception: ${e.message}")
            }
        }
    }

    fun onDeleteHandled() {
        _deleteSuccess.value = false
    }
}