package hr.foi.air.core.network

import hr.foi.air.core.network.data.ExpenseData
import hr.foi.air.core.network.data.ExpensePatchDto
import hr.foi.air.core.network.data.ExpenseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ExpenseApiService {
    @POST("api/Expense")
    suspend fun createExpense(@Body expense: ExpenseData): Response<ExpenseResponse>
    @GET("api/Expense")
    suspend fun getAllExpensesByUser(
        @Query("month") month: Int,
        @Query("categoryId") categoryId: Int?
    ): Response<List<ExpenseData>>
    @GET("api/Expense/{id}")
    suspend fun getExpenseById(
        @Path("id") id: Int
    ): Response<ExpenseData>
    @PATCH("api/Expense/{id}")
    suspend fun patchExpense(
        @Path("id") id: Int,
        @Body patch: ExpensePatchDto
    ): Response<ExpenseResponse>

    @DELETE("api/Expense/{id}")
    suspend fun deleteExpense(
        @Path("id") id: Int
    ): Response<ExpenseResponse>

}
