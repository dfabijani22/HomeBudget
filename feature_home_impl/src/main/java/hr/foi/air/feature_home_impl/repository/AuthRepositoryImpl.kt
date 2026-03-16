package hr.foi.air.feature_home_impl.repository

import hr.foi.air.feature_home_impl.network.AuthApi
import hr.foi.air.feature_home_impl.network.dto.*
import hr.foi.feature_home_api.api.AuthRepository
import hr.foi.feature_home_api.model.*
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun login(request: AuthRequest): ApiResponse<UserResponse> {
        val dtoReq = LoginRequestDto(request.email, request.password)
        val res = api.login(dtoReq)

        val userModel = res.data?.let { dto ->
            UserResponse(
                id = dto.user.id,
                email = dto.user.email,
                name = dto.user.name,
                surname = dto.user.surname,
                token = dto.token
            )
        }

        return ApiResponse(
            success = res.success,
            message = res.message,
            data = userModel
        )
    }

    override suspend fun register(request: RegisterRequest): ApiResponse<UserResponse> {
        val dtoReq = RegisterRequestDto(
            email = request.email,
            password = request.password,
            confirmPassword = request.confirmPassword,
            name = request.name,
            surname = request.surname
        )

        val res = api.register(dtoReq)

        val userModel = res.data?.let { u ->
            UserResponse(
                id = u.id,
                email = u.email,
                name = u.name,
                surname = u.surname,
                token = null
            )
        }

        return ApiResponse(
            success = res.success,
            message = res.message,
            data = userModel
        )
    }
}