package hr.foi.air.feature_home_impl.network

data class ApiResponseDto<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)
