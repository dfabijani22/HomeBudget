package hr.foi.air.feature_expense_list.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import hr.foi.air.core.util.DateFormatter
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hr.foi.air.core.model.Expense

@Composable
fun ExpenseListScreen(
    expenses: List<Expense>,
    isLoading: Boolean,
    onUpdateExpense: (Int) -> Unit
) {

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(expenses) { expense ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation()
            ) {
                Column(Modifier.padding(12.dp)) {

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = expense.name,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(
                            onClick = { onUpdateExpense(expense.Id) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Uredi trošak"
                            )
                        }
                    }
                    Text("Naziv: ${expense.name}")
                    Text("Iznos: %.2f €".format(expense.amount))
                    Text("Datum: ${DateFormatter.format(expense.date)}")
                    Text("Kategorija: ${expense.categoryName}")
                }
            }
        }
    }
}