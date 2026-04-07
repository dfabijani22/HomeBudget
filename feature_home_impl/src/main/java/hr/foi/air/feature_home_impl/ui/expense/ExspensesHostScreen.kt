package hr.foi.air.feature_home_impl.ui.expense

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hr.foi.air.core.feature.ExpenseViewFeature
import hr.foi.air.feature_home_impl.viewModel.expense.ExpensesViewModel
import hr.foi.air.feature_expense_list.ExpenseListFeature
import hr.foi.air.feature_expense_grid.ExpenseGridFeature

@Composable
fun ExpensesHostScreen(
    features: List<ExpenseViewFeature>,
    onUpdateExpense: (Int) -> Unit,
    viewModel: ExpensesViewModel = hiltViewModel()
) {

    println("Features loaded: ${features.size}")

    val expenses by viewModel.expenses.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedFeature by remember { mutableStateOf(features.first()) }
    var selectedMonth by remember { mutableStateOf(java.time.LocalDate.now().monthValue) }
    var selectedCategory by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadExpenses()
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            features.forEach {
                Button(onClick = { selectedFeature = it }) {
                    Text(it.title)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ExpenseFilterBar(
            categories = categories,
            selectedMonth = selectedMonth,
            selectedCategory = selectedCategory,
            onMonthChange = {
                selectedMonth = it
                viewModel.setMonth(it)
            },
            onCategoryChange = {
                selectedCategory = it
                viewModel.setCategory(it)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        println("Rendering feature: ${selectedFeature.id}")

        when (val feature = selectedFeature) {

            is ExpenseListFeature -> feature.Render(
                expenses = expenses,
                isLoading = isLoading,
                onUpdateExpense = onUpdateExpense
            )

            is ExpenseGridFeature -> feature.Render(
                expenses = expenses,
                isLoading = isLoading,
                onUpdateExpense = onUpdateExpense
            )
        }
    }
}