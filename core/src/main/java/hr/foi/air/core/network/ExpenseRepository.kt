package hr.foi.air.core.network

import hr.foi.air.core.network.data.ExpenseData
import hr.foi.air.core.network.data.ExpenseResponse
import retrofit2.Response

class ExpenseRepository(
    private val expenseApi: ExpenseApiService = RetrofitInstance.expenseApi
) {
    suspend fun addExpense(expense: ExpenseData): Response<ExpenseResponse> =
        expenseApi.createExpense(expense)
    suspend fun getAllExpensesByUser(month: Int, categoryId: Int?): Response<List<ExpenseData>> =
        expenseApi.getAllExpensesByUser(month, categoryId ?: 0)
}
