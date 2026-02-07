package hr.foi.air.feature_home_impl.ui.category

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val CATEGORIES_ROUTE = "categories"

fun NavGraphBuilder.categoriesNav() {
    composable(route = CATEGORIES_ROUTE) {
        CategoriesListScreen()
    }
}
