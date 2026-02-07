package hr.foi.air.core.network

import hr.foi.air.core.network.data.CategoryData
import hr.foi.air.core.network.data.CategoryResponse
import hr.foi.air.core.network.data.ExpenseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CategoryApiService {
    @POST("api/category")
    suspend fun createCategory(@Body expense: CategoryData): Response<CategoryResponse>
    @GET("api/category")
    suspend fun getAllCategoriesByUser(
    ): Response<List<CategoryData>>
}
