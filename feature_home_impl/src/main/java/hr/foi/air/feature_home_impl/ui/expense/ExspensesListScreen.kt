package hr.foi.air.feature_home_impl.ui.expense


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hr.foi.air.feature_home_impl.viewModel.expense.ExpenseListViewModel
import java.util.*
const val UPDATE_EXPENSE_ROUTE = "update_expense/{id}"


@Composable
fun ExpenseListScreen(
    viewModel: ExpenseListViewModel = hiltViewModel(),
    onUpdateExpense: (Int) -> Unit
) {
    val expenses by viewModel.expenses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    val categories = listOf(0 to "Sve", 1 to "Hrana", 2 to "Stanovanje", 3 to "Zabava")
    var selectedCategoryId by remember { mutableStateOf(0) }


    LaunchedEffect(selectedMonth, selectedCategoryId) {
        viewModel.loadExpenses(selectedMonth, selectedCategoryId.takeIf { it != 0 })
    }

    Column(Modifier.padding(16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DropdownMenuBox(
                label = "Mjesec",
                options = (1..12).map { it to "$it" },
                selected = selectedMonth,
                onSelected = { selectedMonth = it }
            )

            DropdownMenuBox(
                label = "Kategorija",
                options = categories,
                selected = selectedCategoryId,
                onSelected = { selectedCategoryId = it }
            )
        }

        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(expenses) { expense ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Row(
                                Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ){
                                Text("Naziv: ${expense.name}", fontWeight = FontWeight.Bold)

                                IconButton(
                                    onClick = {
                                        Log.d("NavTrace", "List expensesNav -> forwarding id=${expense.id}")
                                        if (expense.id != null){
                                        onUpdateExpense(expense.id!!)}
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Uredi trošak"
                                    )
                                }

                            }
                            Text("Iznos: %.2f €".format(expense.amount))
                            Text("Datum: ${expense.date}")
                            Text("Kategorija: ${expense.categoryName}")
                        }
                    }
                }
            }
        }
    }
}
