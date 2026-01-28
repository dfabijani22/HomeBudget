package hr.foi.air.core.network

import hr.foi.air.core.network.data.AuthResponse
import hr.foi.air.core.network.data.RegisterData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterData): Response<AuthResponse>
}
