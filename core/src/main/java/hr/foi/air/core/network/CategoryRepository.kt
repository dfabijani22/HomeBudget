package hr.foi.air.core.network

import hr.foi.air.core.network.data.CategoryData
import hr.foi.air.core.network.data.CategoryResponse
import retrofit2.Response
import javax.inject.Inject

class CategoryRepository(
    private val categoryApi: CategoryApiService = RetrofitInstance.categoryApi){

    suspend fun addCategory(category: CategoryData): Response<CategoryResponse> =
        categoryApi.createCategory(category)
}
