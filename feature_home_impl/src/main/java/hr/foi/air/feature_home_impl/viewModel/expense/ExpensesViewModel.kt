package hr.foi.air.feature_home_impl.viewModel.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.air.core.model.Expense
import hr.foi.feature_home_api.CategoryRepository
import hr.foi.feature_home_api.api.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses = _expenses.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _categories = MutableStateFlow<List<Pair<Int, String>>>(emptyList())
    val categories = _categories.asStateFlow()

    private val currentMonth = java.time.LocalDate.now().monthValue
    private var selectedMonth: Int? = currentMonth
    private var selectedCategory: Int? = null


    init {
        println("ExpenseViewModel init")
        loadCategories()
        loadExpenses()
    }


    fun setMonth(month: Int?) {
        selectedMonth = month
        loadExpenses()
    }

    fun setCategory(categoryId: Int?) {
        selectedCategory = categoryId
        loadExpenses()
    }

    fun refresh() {
        loadExpenses()
    }


     fun loadCategories() {
        viewModelScope.launch {
            println("Corutine started")
            val res = categoryRepository.getCategories()

            _categories.value = if (res.success) {
                listOf(0 to "Sve") +
                        (res.data ?: emptyList()).map { it.id to it.name }
            } else {
                listOf(0 to "Sve")
            }
        }
    }

     fun loadExpenses() {
         println("loadExpenses Called")
         viewModelScope.launch {
             println("Coroutine started expense")

             try {
                 println("Before corutine")
                 val res = repository.getExpenses(
                     month = selectedMonth,
                     categoryId = selectedCategory?.takeIf { it !=0 }
                 )

                 println("API success: ${res.success}")
                 println("API data: ${res.data}")
                 println("API message: ${res.message}")

                 if (res.success) {
                     _expenses.value = res.data?.map {
                         Expense(
                             Id = it.id,
                             name = it.name,
                             amount = it.amount,
                             date = it.date,
                             categoryName = it.categoryName
                         )
                     } ?: emptyList()
                 } else {

                 }

             } catch (e: Exception) {
                 println("EXCEPTION: ${e.message}")
             }

             _isLoading.value = false
         }
    }
}