package hr.foi.air.feature_home_impl.viewModel.expense

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.air.core.network.ExpenseApiService
import hr.foi.air.core.network.ExpenseRepository
import hr.foi.air.core.network.data.ExpenseData
import hr.foi.air.core.network.data.ExpenseDisplayItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val expenseApi: ExpenseApiService
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseDisplayItem>>(emptyList())
    val expenses: StateFlow<List<ExpenseDisplayItem>> = _expenses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadExpenses(month: Int, categoryId: Int?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = expenseApi.getAllExpensesByUser(month, categoryId ?: 0)
                Log.d("ExpenseDebug", "Loaded list: ${response.body()}")
                if (response.isSuccessful) {
                    _expenses.value = response.body()?.map { mapExpenseDataToDisplay(it) } ?: emptyList()
                }
            } catch (e: Exception) {
                // Log error if needed
            } finally {
                _isLoading.value = false
            }
        }
    }
    private fun mapExpenseDataToDisplay(data: ExpenseData): ExpenseDisplayItem {
        return ExpenseDisplayItem(
            id = data.id,
            name = data.name,
            amount = data.amount,
            date = formatDate(data.date),
            categoryName = categoryIdToName(data.categoryId),
            categoryId = data.categoryId
        )
    }

    private fun formatDate(rawDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val date = inputFormat.parse(rawDate)
            outputFormat.format(date ?: rawDate)
        } catch (e: Exception) {
            rawDate
        }
    }

    private fun categoryIdToName(id: Int): String {
        return when (id) {
            1 -> "Hrana"
            2 -> "Stanovanje"
            3 -> "Zabava"
            else -> "Nepoznato"
        }
    }
}
