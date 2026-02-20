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

    val expenseApi: ExpenseApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5003/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExpenseApiService::class.java)
    }

    val categoryApi: CategoryApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5003/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoryApiService::class.java)
    }

}
