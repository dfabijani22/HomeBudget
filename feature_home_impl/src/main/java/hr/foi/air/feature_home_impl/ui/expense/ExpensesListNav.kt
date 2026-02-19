package hr.foi.air.feature_home_impl.ui.expense

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hr.foi.air.feature_home_impl.ui.EXPENSES_ROUTE

fun NavGraphBuilder.expensesNav(onUpdateExpense: (Int) -> Unit
    ) {
        composable(EXPENSES_ROUTE) {
            ExpenseListScreen(
                onUpdateExpense = { id ->
                    println("expensesNav -> forwarding id=$id")
                    onUpdateExpense(id)
                }
            )
        }
    }
