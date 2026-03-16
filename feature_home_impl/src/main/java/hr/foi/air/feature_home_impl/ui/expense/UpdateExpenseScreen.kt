package hr.foi.air.feature_home_impl.ui.expense

import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hr.foi.air.feature_home_impl.viewModel.expense.UpdateExpenseViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateExpenseScreen(
    id: Int,
    viewModel: UpdateExpenseViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val expense by viewModel.expense.collectAsState()
    val updateSuccess by viewModel.updateSuccess.collectAsState()
    val deleteSuccess by viewModel.deleteSuccess.collectAsState()
    val categories by viewModel.categories.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadExpense(id)
    }

    LaunchedEffect(updateSuccess) {
        if (updateSuccess) {
            viewModel.clearMessages()
            onBack()
        }
    }

    LaunchedEffect(deleteSuccess) {
        if (deleteSuccess) {
            viewModel.clearMessages()
            onBack()
        }
    }


    var selectedLocalDate by remember { mutableStateOf(LocalDate.now()) }

    LaunchedEffect(expense?.date) {
        expense?.date?.let { raw ->
            selectedLocalDate = parseToLocalDateSafe(raw)
        }
    }

    val readableDate = remember(selectedLocalDate) {
        selectedLocalDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    }

    val isoForApi = remember(selectedLocalDate) {
        selectedLocalDate
            .atStartOfDay(ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_INSTANT)
    }



    if (expense == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    var name by rememberSaveable { mutableStateOf(expense!!.name) }
    var amount by rememberSaveable { mutableStateOf(expense!!.amount.toString()) }

    val parsedLocalDate = remember {
        try {
            val instant = java.time.Instant.parse(expense!!.date)
            instant.atZone(ZoneId.systemDefault()).toLocalDate()
        } catch (e: Exception) {
            LocalDate.now()
        }
    }





    var categoryId by rememberSaveable { mutableStateOf(expense!!.categoryId) }
    var showDeleteDialog by remember { mutableStateOf(false) }


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


        Text("Datum: $readableDate")

        Button(
            onClick = {
                val dialog = DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        selectedLocalDate = LocalDate.of(year, month + 1, day)
                    },
                    selectedLocalDate.year,
                    selectedLocalDate.monthValue - 1,
                    selectedLocalDate.dayOfMonth
                )
                dialog.show()
            }
        ) {
            Text("Odaberi datum")
        }


        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {

            OutlinedTextField(
                readOnly = true,
                value = categories.firstOrNull { it.id == categoryId }?.name ?: "",
                onValueChange = {},
                label = { Text("Kategorija") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { cat ->
                    DropdownMenuItem(
                        text = { Text(cat.name) },
                        onClick = {
                            categoryId = cat.id
                            expanded = false
                        }
                    )
                }
            }
        }


        Button(
            onClick = {
                val parsedAmount = amount.toDoubleOrNull()

                if (parsedAmount == null || name.isBlank()) return@Button

                Log.d("UpdateExpense", "Updating ID=$id")

                viewModel.updateExpense(
                    id = expense!!.id,
                    name = name,
                    amount = parsedAmount,
                    date = isoForApi,
                    categoryId = categoryId
                )
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

private fun parseToLocalDateSafe(raw: String): LocalDate {
    return try {
        Instant.parse(raw).atZone(ZoneId.systemDefault()).toLocalDate()
    } catch (_: Exception) {
        try {
            LocalDateTime.parse(raw).toLocalDate()
        } catch (_: Exception) {
            try {
                LocalDate.parse(raw.take(10))
            } catch (_: Exception) {
                LocalDate.now()
            }
        }
    }
}
