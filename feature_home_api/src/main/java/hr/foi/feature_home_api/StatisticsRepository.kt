package hr.foi.feature_home_api

import hr.foi.feature_home_api.model.ApiResponse
import hr.foi.feature_home_api.model.MonthlyBudgetRequest
import hr.foi.feature_home_api.model.MonthlyBudgetResponse

interface StatisticsRepository {

    suspend fun getMonthlyBudget(
        year: Int? = null,
        month: Int? = null,
    ): ApiResponse<MonthlyBudgetResponse>

    suspend fun setMonthlyBudget(request: MonthlyBudgetRequest): ApiResponse<MonthlyBudgetResponse>
}