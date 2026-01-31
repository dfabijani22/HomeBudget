package hr.foi.air.feature_home_impl.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home"
const val NEW_EXPENSE_ROUTE = "new_expense"
fun NavGraphBuilder.homeNav(onLogout: () -> Unit, onAddExpense: () -> Unit) {
    composable(route = HOME_ROUTE) {
        HomeScreen(onLogout = onLogout, onAddExpense = onAddExpense)
    }
}
