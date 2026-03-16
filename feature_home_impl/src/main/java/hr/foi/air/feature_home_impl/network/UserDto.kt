package hr.foi.air.feature_home_impl.network.dto

data class UserDto(
    val id: Int,
    val email: String,
    val name: String?,
    val surname: String?
)