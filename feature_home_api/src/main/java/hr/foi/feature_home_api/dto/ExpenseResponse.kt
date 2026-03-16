package hr.foi.feature_home_api.dto

data class ExpenseResponse (
    val success: Boolean,
    val message: String,
    val expenseId: Int
)