package hr.foi.feature_home_api.model

data class MonthlyBudgetRequest (
    val year: Int,
    val month: Int,
    val amount: Double
)
