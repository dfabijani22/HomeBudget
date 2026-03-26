package hr.foi.air.feature_home_impl.network

data class MonthlyBudgetRequestDto (
    val year: Int,
    val month: Int,
    val amount: Double
)