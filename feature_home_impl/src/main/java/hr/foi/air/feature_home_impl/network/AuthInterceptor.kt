package hr.foi.air.feature_home_impl.network

import hr.foi.air.feature_home_impl.datastore.UserDataStore
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userDataStore: UserDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = userDataStore.currentToken()
        val req = chain.request().newBuilder().apply {
            if (!token.isNullOrBlank()) {
                addHeader("Authorization", "Bearer $token")
            }
        }.build()
        return chain.proceed(req)
    }
}