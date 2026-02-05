package hr.foi.air.feature_home_impl.viewModel.expense

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.air.core.network.ExpenseApiService
import hr.foi.air.core.network.ExpenseRepository
import hr.foi.air.core.network.data.ExpenseData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val expenseApi: ExpenseApiService
) : ViewModel() {

    private val _expenseAdded = MutableStateFlow(false)
    val expenseAdded: StateFlow<Boolean> = _expenseAdded
    fun addExpense(expense: ExpenseData) {
        Log.d("ExpenseDebug", "Expense to send: $expense")
        viewModelScope.launch {
            val response = expenseApi.createExpense(expense)
            println("RESPONSE: ${response.code()} ${response.isSuccessful}")
            if (response.isSuccessful) {
                _expenseAdded.value = true  // oznaka da je unos uspješan
            }else{println("ERROR BODY: ${response.errorBody()?.string()}")}
        }
    }

    fun onSnackShown() {
        _expenseAdded.value = false
    }
}
