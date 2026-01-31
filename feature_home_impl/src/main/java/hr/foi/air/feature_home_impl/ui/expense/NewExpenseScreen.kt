package hr.foi.air.feature_home_impl.ui.expense

import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hr.foi.air.core.network.data.ExpenseData
import hr.foi.air.feature_home_impl.viewModel.expense.AddExpenseViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewExpenseScreen(
    viewModel: AddExpenseViewModel = hiltViewModel(),
    onExpenseAdded: () -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(Date()) }

    val expenseAdded by viewModel.expenseAdded.collectAsState()

    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()) }
    val formattedDate = dateFormat.format(selectedDate)

    val categories = listOf("Hrana" to 1, "Stanovanje" to 2, "Zabava" to 3)
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Pair<String, Int>?>(null) }

    LaunchedEffect(expenseAdded) {
        if (expenseAdded) {
            Toast.makeText(context, "Trošak uspješno dodan!", Toast.LENGTH_LONG).show()
            viewModel.onSnackShown()
            onExpenseAdded()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Naziv") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Iznos") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedCategory?.first ?: "Odaberi kategoriju",
                onValueChange = {},
                label = { Text("Kategorija") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { (label, id) ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            selectedCategory = label to id
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Datum: ${SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(selectedDate)}")
        Button(onClick = {
            val calendar = Calendar.getInstance().apply { time = selectedDate }
            DatePickerDialog(
                context,
                { _, year, month, day ->
                    calendar.set(year, month, day)
                    selectedDate = calendar.time
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }) {
            Text("Odaberi datum")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val parsedAmount = amount.toDoubleOrNull()
                val categoryId = selectedCategory?.second

                if (name.isBlank() || parsedAmount == null || parsedAmount <= 0.0 || categoryId == null) {
                    Toast.makeText(context, "Molimo ispunite sva polja ispravno.", Toast.LENGTH_LONG).show()
                    return@Button
                }

                val expense = ExpenseData(

                    name = name,
                    amount = parsedAmount,
                    date = formattedDate,
                    categoryId = categoryId
                )

                viewModel.addExpense(expense)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Spremi trošak")
        }
    }
}
