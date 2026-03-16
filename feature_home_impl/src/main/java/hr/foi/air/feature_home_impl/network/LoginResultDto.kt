package hr.foi.air.feature_home_impl.network

import hr.foi.air.feature_home_impl.network.dto.UserDto

data class LoginResultDto(
    val token: String,
    val userId: Int,
    val user: UserDto
)