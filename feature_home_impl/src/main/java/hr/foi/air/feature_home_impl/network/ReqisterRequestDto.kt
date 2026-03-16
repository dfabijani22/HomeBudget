package hr.foi.air.feature_home_impl.network.dto

data class RegisterRequestDto(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val name: String,
    val surname: String
)