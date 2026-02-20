package hr.foi.air.core.network.data

data class ExpensePatchDto(
    val name: String? = null,
    val amount: Double? = null,
    val date: String? = null,
    val categoryId: Int? = null
)