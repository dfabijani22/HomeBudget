package hr.foi.air.feature_expense_list

import androidx.compose.runtime.Composable
import hr.foi.air.core.feature.ExpenseViewFeature
import hr.foi.air.core.model.Expense

import hr.foi.air.feature_expense_list.ui.ExpenseListScreen

class ExpenseListFeature : ExpenseViewFeature {

    override val id = "list"
    override val title = "Lista"

    @Composable
    fun Render(
        expenses: List<Expense>,
        isLoading: Boolean,
        onUpdateExpense: (Int) -> Unit
    ) {
        ExpenseListScreen(
            expenses = expenses,
            isLoading = isLoading,
            onUpdateExpense = onUpdateExpense
        )
    }
}