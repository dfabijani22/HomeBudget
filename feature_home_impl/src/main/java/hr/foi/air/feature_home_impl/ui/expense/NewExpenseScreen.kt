import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hr.foi.air.feature_home_impl.viewModel.expense.AddExpenseViewModel
import hr.foi.air.feature_home_impl.viewModel.expense.CategoriesUiState
import java.time.*
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewExpenseScreen(
    viewModel: AddExpenseViewModel = hiltViewModel(),
    onExpenseAdded: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    var selectedLocalDate by remember { mutableStateOf(LocalDate.now()) }

    val expenseAdded by viewModel.expenseAdded.collectAsState()

    val readableDate = remember(selectedLocalDate) {
        selectedLocalDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    }

    val isoUtcForApi = remember(selectedLocalDate) {
        val startOfDay = selectedLocalDate.atStartOfDay(ZoneId.systemDefault())
        val utcInstant = startOfDay.toInstant()
        DateTimeFormatter.ISO_INSTANT.format(utcInstant)
    }

    val categoriesState by viewModel.categoriesState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<hr.foi.feature_home_api.model.CategoryResponse?>(null) }

    LaunchedEffect(expenseAdded) {
        if (expenseAdded) {
            Toast.makeText(context, "Trošak uspješno dodan!", Toast.LENGTH_LONG).show()
            viewModel.resetState()
            onExpenseAdded()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

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
            label = { Text("Iznos (€)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (val state = categoriesState) {
            is CategoriesUiState.Loading -> {
                Text("Učitavanje kategorija…")
            }
            is CategoriesUiState.Error -> {
                Text("Greška: ${state.message}")
                Spacer(Modifier.height(8.dp))
                Button(onClick = { viewModel.loadCategories() }) {
                    Text("Pokušaj ponovno")
                }
            }
            is CategoriesUiState.Success -> {
                val categories = state.categories

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedCategory?.name ?: "Odaberi kategoriju",
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
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat.name) },
                                onClick = {
                                    selectedCategory = cat
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        Text("Datum: $readableDate")

        Button(
            onClick = {
                val today = LocalDate.now()
                val dialog = android.app.DatePickerDialog(
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val parsedAmount = amount.toDoubleOrNull()
                val categoryId = selectedCategory?.id

                if (name.isBlank() || parsedAmount == null || parsedAmount <= 0.0 || categoryId == null) {
                    Toast.makeText(context, "Provjerite sva polja.", Toast.LENGTH_LONG).show()
                    return@Button
                }

                viewModel.addExpense(
                    name = name,
                    amount = parsedAmount,
                    date = isoUtcForApi,
                    categoryId = categoryId
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Spremi trošak")
        }
    }
}
