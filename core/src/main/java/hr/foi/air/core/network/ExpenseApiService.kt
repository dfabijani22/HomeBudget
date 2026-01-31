package hr.foi.air.core.network

import hr.foi.air.core.network.data.ExpenseData
import hr.foi.air.core.network.data.ExpenseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ExpenseApiService {
    @POST("api/Expense")
    suspend fun createExpense(@Body expense: ExpenseData): Response<ExpenseResponse>
}
