package hr.foi.air.feature_home_impl.viewModel.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.feature_home_api.CategoryRepository
import hr.foi.feature_home_api.api.ExpenseRepository
import hr.foi.feature_home_api.model.ExpenseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

data class ExpenseDisplayItem(
    val id: Int,
    val name: String,
    val amount: Double,
    val dateFormatted: String,
    val categoryName: String
)

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseDisplayItem>>(emptyList())
    val expenses = _expenses.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()
    private val currentMonth = java.time.LocalDate.now().monthValue
    private val _selectedMonth = MutableStateFlow<Int?>(currentMonth)
    private val _selectedCategory = MutableStateFlow<Int?>(null)

    private val _categories = MutableStateFlow<List<Pair<Int,String>>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        loadCategories()
        loadExpenses()
    }
    fun setMonth(month: Int?) {
        _selectedMonth.value = month
        loadExpenses()
    }

    fun setCategory(categoryId: Int?) {
        _selectedCategory.value = categoryId
        loadExpenses()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val res = categoryRepository.getCategories()
            if (res.success) {
                val backendCats = res.data ?: emptyList()
                val mapped = backendCats.map { it.id to it.name }
                _categories.value = listOf(0 to "Sve") + mapped
            } else {
                _categories.value = listOf(0 to "Sve")
                _errorMessage.value = res.message
            }
        }
    }

    fun loadExpenses() {
        viewModelScope.launch {
            _isLoading.value = true

            val res = repository.getExpenses(
                month = _selectedMonth.value,
                categoryId = _selectedCategory.value
            )

            if (res.success) {
                _expenses.value = res.data?.map { it.toDisplayItem() } ?: emptyList()
            } else {
                _errorMessage.value = res.message
            }

            _isLoading.value = false
        }
    }

    private fun ExpenseResponse.toDisplayItem(): ExpenseDisplayItem {
        return ExpenseDisplayItem(
            id = this.id,
            name = this.name,
            amount = this.amount,
            dateFormatted = formatDate(this.date),
            categoryName = this.categoryName
        )
    }

    fun formatDate(rawDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val parsed = inputFormat.parse(rawDate)
            outputFormat.format(parsed ?: rawDate)
        } catch (e: Exception) {
            rawDate
        }
    }

    }