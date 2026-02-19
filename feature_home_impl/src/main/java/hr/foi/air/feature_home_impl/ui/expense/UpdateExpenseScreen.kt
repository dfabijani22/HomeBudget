package hr.foi.air.feature_home_impl.ui.expense

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hr.foi.air.core.network.data.ExpensePatchDto
import hr.foi.air.feature_home_impl.viewModel.expense.UpdateExpenseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateExpenseScreen(
    id: Int,
    viewModel: UpdateExpenseViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val expense by viewModel.expense.collectAsState()
    val updateSuccess by viewModel.updateSuccess.collectAsState()
    val deleteSuccess by viewModel.deleteSuccess.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadExpense(id)
    }

    LaunchedEffect(updateSuccess) {
        if (updateSuccess) {
            onBack()
            viewModel.onSnackShown()
        }
    }

    LaunchedEffect(deleteSuccess) {
        if (deleteSuccess) {
            onBack()            // Pop back to list
            viewModel.onDeleteHandled()
        }
    }


    if (expense == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    var name by rememberSaveable { mutableStateOf(expense!!.name) }
    var amount by rememberSaveable { mutableStateOf(expense!!.amount.toString()) }
    var date by rememberSaveable { mutableStateOf(expense!!.date) }
    var categoryId by rememberSaveable { mutableStateOf(expense!!.categoryId) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val categories = listOf(
        1 to "Hrana",
        2 to "Stanovanje",
        3 to "Zabava"
    )

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Uredi trošak", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Naziv") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Iznos (€)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Datum") },
            modifier = Modifier.fillMaxWidth()
        )

        Box {
            OutlinedTextField(
                value = categories.firstOrNull { it.first == categoryId }?.second ?: "",
                onValueChange = {},
                label = { Text("Kategorija") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { (id, text) ->
                    DropdownMenuItem(
                        text = { Text(text) },
                        onClick = {
                            categoryId = id
                            expanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                Log.d("NavTrace", "CLICK @ExpenseListScreen with id=${expense}")
                val original = expense!!
                val parsedAmount = amount.toDoubleOrNull()

                if (parsedAmount == null || name.isBlank()) return@Button

                val patch = ExpensePatchDto(
                    name = name.takeIf { it != original.name },
                    amount = parsedAmount.takeIf { it != original.amount },
                    date = date.takeIf { it != original.date },
                    categoryId = categoryId.takeIf { it != original.categoryId }
                )

                viewModel.updateExpense(original.id!!, patch)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Spremi promjene")
        }

        Button(
            onClick = { showDeleteDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Obriši trošak")
        }



        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Brisanje troška") },
                text = { Text("Jesi sigurna da želiš obrisati ovaj trošak?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deleteExpense(id)
                        showDeleteDialog = false
                    }) {
                        Text("Obriši")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Odustani")
                    }
                }
            )
        }

    }
}