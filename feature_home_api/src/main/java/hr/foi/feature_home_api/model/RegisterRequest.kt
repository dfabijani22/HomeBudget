package hr.foi.feature_home_api.model

data class RegisterRequest(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val name: String,
    val surname: String
)