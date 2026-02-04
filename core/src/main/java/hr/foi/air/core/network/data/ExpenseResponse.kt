package hr.foi.air.core.network.data

data class ExpenseResponse (
    val success: Boolean,
    val message: String,
    val expenseId: Int
)
