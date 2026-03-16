package hr.foi.feature_home_api.api

import hr.foi.feature_home_api.model.*

interface AuthRepository {
    suspend fun login(request: AuthRequest): ApiResponse<UserResponse>
    suspend fun register(request: RegisterRequest): ApiResponse<UserResponse>
}