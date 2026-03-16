package hr.foi.air.feature_home_impl.viewModel.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.feature_home_api.CategoryRepository
import hr.foi.feature_home_api.api.ExpenseRepository
import hr.foi.feature_home_api.model.CategoryResponse
import hr.foi.feature_home_api.model.ExpenseRequest
import hr.foi.feature_home_api.model.ExpenseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExpenseUi(
    val id: Int,
    val name: String,
    val amount: Double,
    val date: String,
    val categoryId: Int
)

@HiltViewModel
class UpdateExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _expense = MutableStateFlow<ExpenseUi?>(null)
    val expense: StateFlow<ExpenseUi?> = _expense

    private val _categories = MutableStateFlow<List<CategoryResponse>>(emptyList())
    val categories: StateFlow<List<CategoryResponse>> = _categories

    private val _updateSuccess = MutableStateFlow(false)
    val updateSuccess: StateFlow<Boolean> = _updateSuccess

    private val _deleteSuccess = MutableStateFlow(false)
    val deleteSuccess: StateFlow<Boolean> = _deleteSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val res = categoryRepository.getCategories()
            if (res.success) {
                _categories.value = res.data ?: emptyList()
            }
        }
    }

    fun loadExpense(id: Int) {
        viewModelScope.launch {
            val res = repository.getById(id)

            res.data?.let { dto ->
                _expense.value = dto.toUi()
            } ?: run {
                _errorMessage.value = res.message ?: "Greška pri učitavanju troška"
            }
        }
    }
    fun updateExpense(
        id: Int,
        name: String,
        amount: Double,
        date: String,
        categoryId: Int
    ) {
        viewModelScope.launch {

            val request = ExpenseRequest(
                name = name,
                amount = amount,
                date = date,
                categoryId = categoryId
            )

            val res = repository.update(id, request)

            if (res.success) {
                _updateSuccess.value = true
            } else {
                _errorMessage.value = res.message ?: "Greška pri spremanju troška"
            }
        }
    }
    fun deleteExpense(id: Int) {
        viewModelScope.launch {
            val res = repository.delete(id)
            if (res.success) {
                _deleteSuccess.value = true
            } else {
                _errorMessage.value = res.message ?: "Greška pri brisanju"
            }
        }
    }

    fun clearMessages() {
        _errorMessage.value = null
        _updateSuccess.value = false
        _deleteSuccess.value = false
    }

    fun onDeleteHandled() {
        _deleteSuccess.value = false
    }
}

private fun ExpenseResponse.toUi(): ExpenseUi =
    ExpenseUi(
        id = id,
        name = name,
        amount = amount,
        date = date,
        categoryId = categoryId
    )