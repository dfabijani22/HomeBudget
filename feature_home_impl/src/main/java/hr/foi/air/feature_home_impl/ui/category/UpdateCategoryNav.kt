package hr.foi.air.feature_home_impl.ui.category

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
const val UPDATE_CATEGORY_NAV = "update_category/{id}"

fun NavGraphBuilder.updateCategoryNav(
    onBack: () -> Unit
) {
    composable(
        route = UPDATE_CATEGORY_NAV,
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) { entry ->
        val id = entry.arguments!!.getInt("id")

        UpdateCategoryScreen(
            id = id,
            onBack = onBack
        )
    }
}

