package hr.foi.air.feature_expense_grid

import androidx.compose.runtime.Composable
import hr.foi.air.core.feature.ExpenseViewFeature
import hr.foi.air.core.model.Expense
import hr.foi.air.feature_expense_grid.ui.ExpenseGridScreen

class ExpenseGridFeature : ExpenseViewFeature {

    override val id = "grid"
    override val title = "Grid"

    @Composable
    fun Render(
        expenses: List<Expense>,
        isLoading: Boolean,
        onUpdateExpense: (Int) -> Unit
    ) {
        ExpenseGridScreen(
            expenses,
            isLoading,
            onUpdateExpense
        )
    }
}