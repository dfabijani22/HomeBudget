package hr.foi.air.feature_home_impl.ui.category

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hr.foi.air.feature_home_impl.ui.expense.NewCategoryScreen

const val NEW_CATEGORY_ROUTE = "new_category"

fun NavGraphBuilder.newCategoryNav(onCategoryAdded: () -> Unit) {
    composable(route = NEW_CATEGORY_ROUTE) {
        NewCategoryScreen(onCategoryAdded = onCategoryAdded)
    }
}
