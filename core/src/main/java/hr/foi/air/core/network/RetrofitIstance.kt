package hr.foi.air.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val authApi: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5003/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
}
