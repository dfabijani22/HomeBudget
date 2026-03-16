package hr.foi.feature_home_api.api

import hr.foi.feature_home_api.model.*

interface ExpenseRepository {

    suspend fun getExpenses(
        month: Int? = null,
        categoryId: Int? = null
    ): ApiResponse<List<ExpenseResponse>>
    suspend fun getById(id: Int): ApiResponse<ExpenseResponse>
    suspend fun addExpense(request: ExpenseRequest): ApiResponse<ExpenseResponse>
    suspend fun update(id: Int, request: ExpenseRequest): ApiResponse<ExpenseResponse>
    suspend fun delete(id: Int): ApiResponse<ExpenseResponse>
}