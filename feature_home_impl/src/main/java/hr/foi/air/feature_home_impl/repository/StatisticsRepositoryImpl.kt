package hr.foi.air.feature_home_impl.repository

import hr.foi.air.feature_home_impl.network.MonthlyBudgetRequestDto
import hr.foi.air.feature_home_impl.network.MonthlyBudgetResponseDto
import hr.foi.air.feature_home_impl.network.StatisticsApi
import hr.foi.feature_home_api.StatisticsRepository
import hr.foi.feature_home_api.model.ApiResponse
import hr.foi.feature_home_api.model.MonthlyBudgetRequest
import hr.foi.feature_home_api.model.MonthlyBudgetResponse
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val api: StatisticsApi
) : StatisticsRepository{
    override suspend fun getMonthlyBudget(
        year: Int?,
        month: Int?
    ): ApiResponse<MonthlyBudgetResponse> {
        val res = api.getMonthlyBudget(year, month)
        return ApiResponse(
            success = res.success,
            message = res.message,
            data = res.data?.toModel()
        )
    }

    override suspend fun setMonthlyBudget(request: MonthlyBudgetRequest): ApiResponse<MonthlyBudgetResponse> {

        val dtoRequest = request.toDto()
        val res = api.setMonthlyBudget(dtoRequest)

        return ApiResponse(
            success = res.success,
            message = res.message,
            data = res.data?.toModel()
        )
    }
}

fun MonthlyBudgetRequest.toDto() = MonthlyBudgetRequestDto(year, month, amount)

fun MonthlyBudgetResponseDto.toModel() =
    MonthlyBudgetResponse(id, year, month, amount)
