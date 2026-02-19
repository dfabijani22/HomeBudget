package hr.foi.air.feature_home_impl.viewModel.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.air.core.network.CategoryApiService
import hr.foi.air.core.network.ExpenseApiService
import hr.foi.air.core.network.data.CategoryData
import hr.foi.air.core.network.data.CategoryDisplayItem
import hr.foi.air.core.network.data.ExpenseData
import hr.foi.air.core.network.data.ExpenseDisplayItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoryApi: CategoryApiService
) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryDisplayItem>>(emptyList())
    val categories: StateFlow<List<CategoryDisplayItem>> = _categories

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = categoryApi.getAllCategoriesByUser()
                if (response.isSuccessful) {
                    _categories.value = response.body()?.map { mapCategoryDataToDisplay(it) } ?: emptyList()
                }
            } catch (e: Exception) {
            } finally {
                _isLoading.value = false
            }
        }
    }
    private fun mapCategoryDataToDisplay(data: CategoryData): CategoryDisplayItem {
        return CategoryDisplayItem(
            name = data.name,
            description = data.description
        )
    }
}
