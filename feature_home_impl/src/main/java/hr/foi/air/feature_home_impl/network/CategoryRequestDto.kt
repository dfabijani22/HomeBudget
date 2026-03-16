package hr.foi.air.feature_home_impl.network

data class CategoryRequestDto(

    val name: String,
    val description: String,
    val isDefault: Boolean

)