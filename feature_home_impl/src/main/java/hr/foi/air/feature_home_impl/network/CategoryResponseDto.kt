package hr.foi.air.feature_home_impl.network

data class CategoryResponseDto(
    val id: Int,
    val name: String,
    val description: String,
    val isDefault: Boolean
)