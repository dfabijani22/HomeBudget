package hr.foi.air.feature_home_impl.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home"
const val NEW_EXPENSE_ROUTE = "new_expense"
const val EXPENSES_ROUTE = "expenses"
const val NEW_CATEGORY_ROUTE = "new_category"
const val CATEGORIES_ROUTE = "categories"
const val UPDATE_EXPENSE_ROUTE = "update_expense"
const val UPDATE_EXPENSE_NAV = "update_expense/{id}"
const val UPDATE_CATEGORY_ROUTE = "update_expense"
const val UPDATE_CATEGORY_NAV = "update_expense/{id}"





fun NavGraphBuilder.homeNav(onLogout: () -> Unit, onAddExpense: () -> Unit, onViewExpenses : () -> Unit, onAddCategory: () -> Unit, onViewCategories: () -> Unit) {
    composable(route = HOME_ROUTE) {
        HomeScreen(onLogout = onLogout, onAddExpense = onAddExpense, onViewExpenses = onViewExpenses, onAddCategory = onAddCategory, onViewCategories = onViewCategories)
    }
}
