package hr.foi.feature_home_api.model

data class CategoryResponse (
    val id: Int,
    val name: String,
    val description: String,
    val isDefault: Boolean
)