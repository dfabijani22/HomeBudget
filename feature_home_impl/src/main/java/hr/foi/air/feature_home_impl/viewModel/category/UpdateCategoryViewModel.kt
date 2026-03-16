package hr.foi.air.feature_home_impl.viewModel.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.feature_home_api.CategoryRepository
import hr.foi.feature_home_api.model.CategoryRequest
import hr.foi.feature_home_api.model.CategoryResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryUi(
    val id: Int,
    val name: String?,
    val description: String?,
    val isDefault: Boolean
)

@HiltViewModel
class UpdateCategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _category = MutableStateFlow<CategoryUi?>(null)
    val category: StateFlow<CategoryUi?> = _category

    private val _updateSuccess = MutableStateFlow(false)
    val updateSuccess: StateFlow<Boolean> = _updateSuccess

    private val _deleteSuccess = MutableStateFlow(false)
    val deleteSuccess: StateFlow<Boolean> = _deleteSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadCategory(id: Int) {
        viewModelScope.launch {
            val res = repository.getById(id)
            res.data?.let {
                _category.value = it.toUi()
            } ?: run {
                _errorMessage.value = res.message ?: "Greška pri učitavanju kategorije"
            }

        }
    }

    fun updateCategory(
        id: Int,
        name: String,
        description: String,
        isDefault: Boolean
    ) {
        viewModelScope.launch {
            val request = CategoryRequest(
                name = name,
                description = description,
                isDefault = isDefault
            )

            val res = repository.update(id, request)

            if (res.success) {
                _updateSuccess.value = true
            } else {
                _errorMessage.value = res.message ?: "Greška pri spremanju"
            }
        }
    }

    fun deleteCategory(id: Int) {
        viewModelScope.launch {
            val res = repository.delete(id)

            if (res.success) {
                _deleteSuccess.value = true
            } else {
                _errorMessage.value = res.message ?: "Greška pri brisanju"
            }
        }
    }

    fun onDeleteHandled() {
        _deleteSuccess.value = false
    }
    fun clearMessages() {
        _errorMessage.value = null
        _updateSuccess.value = false
        _deleteSuccess.value = false
    }
}

private fun CategoryResponse.toUi(): CategoryUi =
    CategoryUi(
        id = id ?: 0,
        name = name,
        description = description,
        isDefault = isDefault
    )