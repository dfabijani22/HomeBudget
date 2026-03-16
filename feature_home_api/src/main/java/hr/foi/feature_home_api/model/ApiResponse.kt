package hr.foi.feature_home_api.model

data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?
)