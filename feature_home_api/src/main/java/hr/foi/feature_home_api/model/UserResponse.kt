package hr.foi.feature_home_api.model

data class UserResponse(
    val id: Int,
    val email: String,
    val name: String?,
    val surname: String?,
    val token: String? = null
)