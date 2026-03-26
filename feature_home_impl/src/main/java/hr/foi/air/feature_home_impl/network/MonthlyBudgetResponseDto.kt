package hr.foi.air.feature_home_impl.network

data class MonthlyBudgetResponseDto (
    val id: Int,
    val year: Int,
    val month: Int,
    val amount: Double
)