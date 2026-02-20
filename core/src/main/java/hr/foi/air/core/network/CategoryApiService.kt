package hr.foi.air.core.network

import hr.foi.air.core.network.data.CategoryData
import hr.foi.air.core.network.data.CategoryRequest
import hr.foi.air.core.network.data.CategoryResponse
import hr.foi.air.core.network.data.ExpenseData
import hr.foi.air.core.network.data.ExpenseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryApiService {
    @POST("api/category")
    suspend fun createCategory(@Body expense: CategoryData): Response<CategoryResponse>
    @GET("api/category")
    suspend fun getAllCategoriesByUser(
    ): Response<List<CategoryData>>
    @PATCH("api/category/{id}")
    suspend fun updateCategory(
        @Path("id") id: Int,
        @Body request: CategoryRequest
    ): Response<CategoryResponse>
    @GET("api/category/{id}")
    suspend fun getCategoryById(
        @Path("id") id: Int
    ): Response<CategoryData>
    @DELETE("api/category/{id}")
    suspend fun deleteCategory(
        @Path("id") id: Int
    ): Response<CategoryResponse>
}
