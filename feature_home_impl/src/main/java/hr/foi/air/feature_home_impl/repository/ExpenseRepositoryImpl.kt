package hr.foi.air.feature_home_impl.repository

import hr.foi.air.feature_home_impl.network.ExpenseApi
import hr.foi.air.feature_home_impl.network.ExpenseRequestDto
import hr.foi.air.feature_home_impl.network.ExpenseResponseDto
import hr.foi.feature_home_api.api.ExpenseRepository
import hr.foi.feature_home_api.model.*
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val api: ExpenseApi
) : ExpenseRepository {

    override suspend fun getExpenses(month: Int?, categoryId: Int?)
            : ApiResponse<List<ExpenseResponse>> {
        val res = api.getExpenses(month, categoryId)
        val mapped = res.data?.map { it.toModel() } ?: emptyList()
        return ApiResponse(res.success, res.message, mapped)
    }

    override suspend fun getById(id: Int): ApiResponse<ExpenseResponse> {
        val res = api.getById(id)
        return ApiResponse(res.success, res.message, res.data?.toModel())
    }

    override suspend fun addExpense(request: ExpenseRequest): ApiResponse<ExpenseResponse> {
        val dtoReq = ExpenseRequestDto(
            name = request.name,
            amount = request.amount,
            date = request.date,
            categoryId = request.categoryId
        )
        val res = api.addExpense(dtoReq)
        return ApiResponse(res.success, res.message, res.data?.toModel())
    }

    override suspend fun update(id: Int, request: ExpenseRequest): ApiResponse<ExpenseResponse> {
        val dtoReq = ExpenseRequestDto(
            name = request.name,
            amount = request.amount,
            date = request.date,
            categoryId = request.categoryId
        )
        val res = api.updateExpense(id, dtoReq)
        return ApiResponse(res.success, res.message, res.data?.toModel())
    }

    override suspend fun delete(id: Int): ApiResponse<ExpenseResponse> {
        val res = api.deleteExpense(id)
        return ApiResponse(res.success, res.message, res.data?.toModel())
    }
}

private fun ExpenseResponseDto.toModel(): ExpenseResponse =
    ExpenseResponse(
        id = id,
        name = name,
        amount = amount,
        date = date,
        categoryId = categoryId,
        categoryName = categoryName
    )