package hr.foi.air.feature_home_impl.ui.expense

import android.util.Log
import androidx.compose.foundation.layout.*
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

const val UPDATE_EXPENSE_ROUTE = "update_expense/{id}"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
    label: String,
    options: List<Pair<Int, String>>,
    selected: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = options.firstOrNull { it.first == selected }?.second ?: "",
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { (id, name) ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        onSelected(id)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ExpenseListScreen(
    viewModel: ExpenseListViewModel = hiltViewModel(),
    onUpdateExpense: (Int) -> Unit
) {
    val expenses by viewModel.expenses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val categories by viewModel.categories.collectAsState()

    val monthNames = remember {
        listOf(
            1 to "Siječanj",
            2 to "Veljača",
            3 to "Ožujak",
            4 to "Travanj",
            5 to "Svibanj",
            6 to "Lipanj",
            7 to "Srpanj",
            8 to "Kolovoz",
            9 to "Rujan",
            10 to "Listopad",
            11 to "Studeni",
            12 to "Prosinac"
        )
    }

    val currentMonth = remember { java.time.LocalDate.now().monthValue }
    var selectedMonth by remember { mutableStateOf(currentMonth) }

    var selectedCategoryId by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadExpenses()
    }
    LaunchedEffect(selectedMonth) {
        viewModel.setMonth(selectedMonth)
    }

    LaunchedEffect(selectedCategoryId) {
        viewModel.setCategory(
            selectedCategoryId.takeIf { it != 0 }
        )
    }

    Column(Modifier.padding(16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DropdownMenuBox(
                label = "Mjesec",
                options = monthNames,
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
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Naziv: ${expense.name}",
                                    fontWeight = FontWeight.Bold
                                )

                                IconButton(
                                    onClick = {
                                        Log.d("NavTrace", "ID=${expense.id}")
                                        onUpdateExpense(expense.id)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Uredi trošak"
                                    )
                                }
                            }

                            Text("Iznos: %.2f €".format(expense.amount))
                            Text("Datum: ${expense.dateFormatted}")
                            Text("Kategorija: ${expense.categoryName}")
                        }
                    }
                }
            }
        }
    }
}