package hr.foi.feature_home_api.model

data class CategoryRequest(
    val name: String,
    val description: String,
    val isDefault: Boolean
)