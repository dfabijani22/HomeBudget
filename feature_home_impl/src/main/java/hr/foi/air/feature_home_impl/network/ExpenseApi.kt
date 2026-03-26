package hr.foi.air.feature_home_impl.network
import retrofit2.http.*

interface ExpenseApi {

    @GET("api/expense")
    suspend fun getExpenses(
        @Query("month") month: Int? = null,
        @Query("year") year: Int? = null,
        @Query("categoryId") categoryId: Int? = null
    ): ApiResponseDto<List<ExpenseResponseDto>>

    @GET("api/expense/{expenseId}")
    suspend fun getById(
        @Path("expenseId") id: Int
    ): ApiResponseDto<ExpenseResponseDto>

    @POST("api/expense")
    suspend fun addExpense(
        @Body req: ExpenseRequestDto
    ): ApiResponseDto<ExpenseResponseDto>

    @PATCH("api/expense/{expenseId}")
    suspend fun updateExpense(
        @Path("expenseId") id: Int,
        @Body req: ExpenseRequestDto
    ): ApiResponseDto<ExpenseResponseDto>

    @DELETE("api/expense/{expenseId}")
    suspend fun deleteExpense(
        @Path("expenseId") id: Int
    ): ApiResponseDto<ExpenseResponseDto>
}