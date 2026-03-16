package hr.foi.air.feature_home_impl.network

import hr.foi.air.feature_home_impl.network.dto.*
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequestDto): ApiResponseDto<LoginResultDto>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequestDto): ApiResponseDto<UserDto>
}