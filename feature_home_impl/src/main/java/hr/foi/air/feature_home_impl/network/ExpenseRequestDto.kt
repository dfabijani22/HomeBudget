package hr.foi.air.feature_home_impl.network


data class ExpenseRequestDto(
    val name: String,
    val amount: Double,
    val date: String,
    val categoryId: Int
)
