package hr.foi.feature_home_api.model

data class ExpenseResponse (
    val id: Int,
    val name: String,
    val amount: Double,
    val date: String,
    val categoryId: Int,
    val categoryName : String

)