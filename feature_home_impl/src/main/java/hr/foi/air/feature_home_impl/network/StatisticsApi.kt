package hr.foi.air.feature_home_impl.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface StatisticsApi {
    @GET("api/monthlybudget")
    suspend fun getMonthlyBudget(
        @Query("year") year: Int? = null,
        @Query("month") month: Int? = null,
    ): ApiResponseDto<MonthlyBudgetResponseDto>

    @PUT("api/monthlybudget")
    suspend fun setMonthlyBudget(
        @Body req: MonthlyBudgetRequestDto
    ): ApiResponseDto<MonthlyBudgetResponseDto>
}