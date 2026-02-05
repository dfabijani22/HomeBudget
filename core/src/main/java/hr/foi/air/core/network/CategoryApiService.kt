package hr.foi.air.core.network

import hr.foi.air.core.network.data.CategoryData
import hr.foi.air.core.network.data.CategoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CategoryApiService {
    @POST("api/category")
    suspend fun createCategory(@Body expense: CategoryData): Response<CategoryResponse>
}
