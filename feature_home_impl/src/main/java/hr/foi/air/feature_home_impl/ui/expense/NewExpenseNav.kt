package hr.foi.air.feature_home_impl.ui.expense

import NewExpenseScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hr.foi.air.feature_home_impl.ui.NEW_EXPENSE_ROUTE

fun NavGraphBuilder.newExpenseNav(onExpenseAdded: () -> Unit) {
    composable(route = NEW_EXPENSE_ROUTE) {
        NewExpenseScreen(onExpenseAdded = onExpenseAdded)
    }
}
