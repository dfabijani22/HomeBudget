package hr.foi.air.core.network

import hr.foi.air.core.network.data.AuthResponse
import hr.foi.air.core.network.data.LoginData
import hr.foi.air.core.network.data.LoginResponse
import hr.foi.air.core.network.data.RegisterData
import retrofit2.Response

class ApiRepository(
    private val authApi: AuthApi = RetrofitInstance.authApi
) {
    suspend fun registerUser(registerData: RegisterData): Response<AuthResponse> {
        return authApi.register(registerData)
    }
    suspend fun login(loginData: LoginData): Response<LoginResponse> {
        return authApi.login(loginData)
    }
}
