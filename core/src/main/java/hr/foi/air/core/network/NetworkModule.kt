package hr.foi.air.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.foi.air.core.network.data.AuthInterceptor
import hr.foi.air.core.network.data.UserDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi =
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5003/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideExpenseApi(userDataStore: UserDataStore): ExpenseApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor {
                runBlocking { userDataStore.getToken() }
            })
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5003/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExpenseApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryApi(userDataStore: UserDataStore): CategoryApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor {
                runBlocking { userDataStore.getToken() }
            })
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5003/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoryApiService::class.java)
    }
}
