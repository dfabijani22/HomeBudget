package hr.foi.air.core.network.data
data class RegisterData(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val firstName: String = "",
    val lastName: String = ""
)
