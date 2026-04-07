package hr.foi.air.feature_home_impl.ui.expense

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hr.foi.air.feature_home_impl.ui.EXPENSES_ROUTE
import hr.foi.air.feature_home_impl.util.FeatureLoader

fun NavGraphBuilder.expensesNav(
    onUpdateExpense: (Int) -> Unit
) {
    composable(EXPENSES_ROUTE) {

        val context = LocalContext.current

        val features = remember {
            FeatureLoader.load(context)
        }

        ExpensesHostScreen(
            features = features,
            onUpdateExpense = { id ->
                println("expensesNav -> forwarding id=$id")
                onUpdateExpense(id)
            }
        )
    }
}