package hr.foi.air.core.network

import hr.foi.air.core.network.data.ExpenseData
import hr.foi.air.core.network.data.ExpenseResponse
import retrofit2.Response
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val api: ExpenseApiService
) {
    suspend fun addExpense(expense: ExpenseData): Response<ExpenseResponse> =
        api.createExpense(expense)
    suspend fun getAllExpensesByUser(month: Int, categoryId: Int?): Response<List<ExpenseData>> =
        api.getAllExpensesByUser(month, categoryId ?: 0)
}
