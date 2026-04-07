package hr.foi.air.core.model

data class Expense(
    val Id: Int,
    val name: String,
    val amount: Double,
    val date: String,
    val categoryName: String
)