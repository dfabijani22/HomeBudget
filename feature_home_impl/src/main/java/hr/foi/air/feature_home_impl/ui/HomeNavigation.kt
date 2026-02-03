package hr.foi.air.feature_home_impl.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home"
const val NEW_EXPENSE_ROUTE = "new_expense"
const val EXPENSES_ROUTE = "expenses"
fun NavGraphBuilder.homeNav(onLogout: () -> Unit, onAddExpense: () -> Unit, onViewExpenses : () -> Unit) {
    composable(route = HOME_ROUTE) {
        HomeScreen(onLogout = onLogout, onAddExpense = onAddExpense, onViewExpenses = onViewExpenses)
    }
}
