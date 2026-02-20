package hr.foi.air.feature_home_impl.ui.category

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val CATEGORIES_ROUTE = "categories"

fun NavGraphBuilder.categoriesNav(
    onUpdateCategory: (Int) -> Unit
) {
    composable(route = CATEGORIES_ROUTE) {
        CategoriesListScreen(
            onUpdateCategory = { id ->
                println("expensesNav -> forwarding id=$id")
                onUpdateCategory(id)
            }
        )
    }
}


