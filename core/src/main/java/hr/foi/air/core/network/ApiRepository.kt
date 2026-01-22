package hr.foi.air.core.network

class ApiRepository {
    suspend fun getTestMessage(): String {
        return RetrofitInstance.api.getTestMessage()
    }
}
