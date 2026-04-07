package hr.foi.air.feature_expense_grid.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hr.foi.air.core.model.Expense
import hr.foi.air.core.util.DateFormatter

@Composable
fun ExpenseGridScreen(
    expenses: List<Expense>,
    isLoading: Boolean,
    onUpdateExpense: (Int) -> Unit
) {

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(expenses) { expense ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    Text(
                        text = expense.name,
                        fontWeight = FontWeight.Bold
                    )

                    Text("Iznos: %.2f €".format(expense.amount))
                    Text("Datum: ${DateFormatter.format(expense.date)}")
                    Text("Kategorija: ${expense.categoryName}")

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { onUpdateExpense(expense.Id) }
                    ) {
                        Text("Uredi")
                    }
                }
            }
        }
    }
}