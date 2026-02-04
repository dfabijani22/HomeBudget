package hr.foi.air.core.network.data

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null,
    val userId: Int? = null
)
