package hr.foi.air.feature_home_impl.viewModel.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.feature_home_api.CategoryRepository
import hr.foi.feature_home_api.model.CategoryResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryDisplayItem(
    val id: Int,
    val name: String,
    val description: String?
)

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryDisplayItem>>(emptyList())
    val categories: StateFlow<List<CategoryDisplayItem>> = _categories

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadCategories() {
        viewModelScope.launch {
            _isLoading.value = true

            val response = repository.getCategories()

            if (response.success) {
                _categories.value = response.data?.map { it.toDisplayItem() } ?: emptyList()
            }

            _isLoading.value = false
        }
    }
}

private fun CategoryResponse.toDisplayItem(): CategoryDisplayItem {
    return CategoryDisplayItem(
        id = this.id ?: 0,
        name = this.name,
        description = this.description)


}