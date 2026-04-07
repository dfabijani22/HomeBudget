package hr.foi.air.feature_home_impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.foi.air.feature_home_impl.network.AuthApi
import hr.foi.air.feature_home_impl.network.AuthInterceptor
import hr.foi.air.feature_home_impl.network.CategoryApi
import hr.foi.air.feature_home_impl.network.ExpenseApi
import hr.foi.air.feature_home_impl.network.StatisticsApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5003/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides @Singleton
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi =
        retrofit.create(CategoryApi::class.java)

    @Provides @Singleton
    fun provideExpenseApi(retrofit: Retrofit): ExpenseApi =
        retrofit.create(ExpenseApi::class.java)

    @Provides @Singleton
    fun provideStatisticsApi(retrofit: Retrofit): StatisticsApi =
        retrofit.create(StatisticsApi::class.java)
}