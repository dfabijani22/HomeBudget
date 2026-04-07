package hr.foi.feature_home_api.model

data class MonthlyBudgetResponse (
    val id: Int,
    val year: Int,
    val month: Int,
    val amount: Double
)

