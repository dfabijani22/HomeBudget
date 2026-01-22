package hr.foi.air.core.network

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/test")
    suspend fun getTestMessage(): String
}
