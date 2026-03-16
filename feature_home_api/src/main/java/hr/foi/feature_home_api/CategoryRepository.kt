package hr.foi.feature_home_api

import hr.foi.feature_home_api.model.ApiResponse
import hr.foi.feature_home_api.model.CategoryRequest
import hr.foi.feature_home_api.model.CategoryResponse


interface CategoryRepository {
    suspend fun getCategories(): ApiResponse<List<CategoryResponse>>
    suspend fun getById(id: Int): ApiResponse<CategoryResponse>
    suspend fun addCategory(request: CategoryRequest): ApiResponse<CategoryResponse>
    suspend fun update(id: Int, request: CategoryRequest): ApiResponse<CategoryResponse>
    suspend fun delete(id: Int): ApiResponse<CategoryResponse>
}
