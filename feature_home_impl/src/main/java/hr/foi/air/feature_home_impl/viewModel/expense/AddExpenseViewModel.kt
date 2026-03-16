package hr.foi.air.feature_home_impl.viewModel.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.feature_home_api.CategoryRepository
import hr.foi.feature_home_api.api.ExpenseRepository
import hr.foi.feature_home_api.model.CategoryResponse
import hr.foi.feature_home_api.model.ExpenseRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CategoriesUiState {
    object Loading : CategoriesUiState
    data class Success(val categories: List<CategoryResponse>) : CategoriesUiState
    data class Error(val message: String) : CategoriesUiState
}

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {


    private val _categoriesState = MutableStateFlow<CategoriesUiState>(CategoriesUiState.Loading)
    val categoriesState: StateFlow<CategoriesUiState> = _categoriesState

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categoriesState.value = CategoriesUiState.Loading
            try {
                val res = categoryRepository.getCategories()
                if (res.success) {
                    _categoriesState.value = CategoriesUiState.Success(res.data ?: emptyList())
                } else {
                    _categoriesState.value = CategoriesUiState.Error("Error")
                }
            } catch (e: Exception) {
                _categoriesState.value = CategoriesUiState.Error(e.message ?: "Greška pri učitavanju kategorija.")
            }
        }
    }


    private val _expenseAdded = MutableStateFlow(false)
    val expenseAdded: StateFlow<Boolean> = _expenseAdded

    fun addExpense(name: String, amount: Double, date: String, categoryId: Int) {
        viewModelScope.launch {
            val request = ExpenseRequest(
                name = name,
                amount = amount,
                date = date,
                categoryId = categoryId
            )

            val res = repository.addExpense(request)
            _expenseAdded.value = res.success
        }
    }

    fun resetState() {
        _expenseAdded.value = false
    }
}