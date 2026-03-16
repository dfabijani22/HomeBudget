package hr.foi.feature_home_api.model

data class ExpenseRequest (
    val name: String,
    val amount: Double,
    val date: String,
    val categoryId: Int
)