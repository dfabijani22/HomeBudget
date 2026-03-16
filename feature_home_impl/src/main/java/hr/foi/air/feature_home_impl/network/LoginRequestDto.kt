package hr.foi.air.feature_home_impl.network.dto

data class LoginRequestDto(
    val email: String,
    val password: String
)