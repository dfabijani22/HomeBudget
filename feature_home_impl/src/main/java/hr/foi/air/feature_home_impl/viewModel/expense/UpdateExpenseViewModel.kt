package hr.foi.air.feature_home_impl.viewModel.expense

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.air.core.network.ExpenseApiService
import hr.foi.air.core.network.data.ExpenseData
import hr.foi.air.core.network.data.ExpensePatchDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateExpenseViewModel @Inject constructor(
    private val expenseApi: ExpenseApiService
) : ViewModel() {

    private val _expense = MutableStateFlow<ExpenseData?>(null)
    val expense: StateFlow<ExpenseData?> = _expense

    private val _updateSuccess = MutableStateFlow(false)
    val updateSuccess: StateFlow<Boolean> = _updateSuccess

    fun loadExpense(id: Int) {
        viewModelScope.launch {
            try {
                val response = expenseApi.getExpenseById(id)
                Log.d("UpdateDebug", "LOADING expense id=$id")
                if (response.isSuccessful) {
                    _expense.value = response.body()
                } else {
                    Log.e("EditExpenseVM", "Error loading expense: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("EditExpenseVM", "Exception: ${e.message}")
            }
        }
    }

    fun updateExpense(id: Int, patch: ExpensePatchDto) {
        Log.d("EditExpenseVM", "PATCH: $patch")

        viewModelScope.launch {
            try {
                val response = expenseApi.patchExpense(id, patch)
                if (response.isSuccessful) {
                    _updateSuccess.value = true
                } else {
                    Log.e("EditExpenseVM", "Error updating: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("EditExpenseVM", "Exception: ${e.message}")
            }
        }
    }

    fun onSnackShown() {
        _updateSuccess.value = false
    }
}