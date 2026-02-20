package hr.foi.air.core.network.data

data class ExpenseDisplayItem(
    val id: Int? = null,
    val name: String,
    val amount: Double,
    val date: String,
    val categoryName: String,
    val categoryId: Int
)
