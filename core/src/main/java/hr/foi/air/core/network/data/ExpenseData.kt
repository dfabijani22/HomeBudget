package hr.foi.air.core.network.data

data class ExpenseData (
    val id: Int? = null,
    val name: String,
    val amount: Double,
    val date: String,
    val categoryId: Int
)
