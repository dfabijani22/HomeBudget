package hr.foi.air.feature_home_impl.repository

import hr.foi.air.feature_home_impl.network.CategoryApi
import hr.foi.air.feature_home_impl.network.CategoryRequestDto
import hr.foi.air.feature_home_impl.network.CategoryResponseDto

import hr.foi.feature_home_api.CategoryRepository
import hr.foi.feature_home_api.model.ApiResponse
import hr.foi.feature_home_api.model.CategoryRequest
import hr.foi.feature_home_api.model.CategoryResponse
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApi
) : CategoryRepository {

    override suspend fun getCategories(): ApiResponse<List<CategoryResponse>> {
        val res = api.getCategories()

        val mapped = res.data?.map { it.toModel() } ?: emptyList()

        return ApiResponse(
            success = res.success,
            message = res.message,
            data = mapped
        )
    }

    override suspend fun getById(id: Int): ApiResponse<CategoryResponse> {
        val res = api.getById(id)

        return ApiResponse(
            success = res.success,
            message = res.message,
            data = res.data?.toModel()
        )
    }

    override suspend fun addCategory(request: CategoryRequest): ApiResponse<CategoryResponse> {
        val dtoReq = CategoryRequestDto(
            name = request.name,
            description = request.description,
            isDefault = request.isDefault
        )

        val res = api.addCategory(dtoReq)

        return ApiResponse(
            success = res.success,
            message = res.message,
            data = res.data?.toModel()
        )
    }

    override suspend fun update(id: Int, request: CategoryRequest): ApiResponse<CategoryResponse> {
        val dtoReq = CategoryRequestDto(
            name = request.name,
            description = request.description,
            isDefault = request.isDefault
        )

        val res = api.update(id, dtoReq)

        return ApiResponse(
            success = res.success,
            message = res.message,
            data = res.data?.toModel()
        )
    }

    override suspend fun delete(id: Int): ApiResponse<CategoryResponse> {
        val res = api.delete(id)

        return ApiResponse(
            success = res.success,
            message = res.message,
            data = res.data?.toModel()
        )
    }
}

private fun CategoryResponseDto.toModel(): CategoryResponse =
    CategoryResponse(
        id = id,
        name = name,
        description = description,
        isDefault = isDefault
    )