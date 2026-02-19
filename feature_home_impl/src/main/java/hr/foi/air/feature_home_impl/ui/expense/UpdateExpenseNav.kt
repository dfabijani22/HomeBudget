package hr.foi.air.feature_home_impl.ui.expense

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
const val UPDATE_EXPENSE_NAV = "update_expense/{id}"




fun NavGraphBuilder.updateExpenseNav(
    onBack: () -> Unit
) {
    composable(
        route = UPDATE_EXPENSE_NAV,
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) { entry ->
        val id = entry.arguments!!.getInt("id")

        UpdateExpenseScreen(
            id = id,
            onBack = onBack
        )
    }
}


