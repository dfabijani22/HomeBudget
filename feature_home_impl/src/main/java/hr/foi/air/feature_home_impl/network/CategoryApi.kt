package hr.foi.air.feature_home_impl.network
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryApi {
    @GET("api/category")
    suspend fun getCategories(
    ): ApiResponseDto<List<CategoryResponseDto>>

    @GET("api/category/{id}")
    suspend fun getById(
        @Path("id") id: Int
    ): ApiResponseDto<CategoryResponseDto>

    @POST("api/category")
    suspend fun addCategory(
        @Body expense: CategoryRequestDto
    ): ApiResponseDto<CategoryResponseDto>

    @PATCH("api/category/{id}")
    suspend fun update(
        @Path("id") id: Int,
        @Body request: CategoryRequestDto
    ): ApiResponseDto<CategoryResponseDto>

    @DELETE("api/category/{id}")
    suspend fun delete(
        @Path("id") id: Int
    ): ApiResponseDto<CategoryResponseDto>
}