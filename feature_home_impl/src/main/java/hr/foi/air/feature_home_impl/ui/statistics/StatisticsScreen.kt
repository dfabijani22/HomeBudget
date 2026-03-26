package hr.foi.air.feature_home_impl.ui.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hr.foi.air.feature_home_impl.viewModel.statistics.StatisticsViewModel
import java.time.LocalDate
import kotlin.math.absoluteValue

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val monthlyBudget by viewModel.monthlyBudget.collectAsState()
    val monthlySpending by viewModel.monthlySpending.collectAsState()
    val alertMessage by viewModel.alertMessage.collectAsState()
    val updateSuccess by viewModel.updateSuccess.collectAsState()
    val categorySummary by viewModel.categorySummary.collectAsState()
    val trendData by viewModel.trend.collectAsState()

    var showEditDialog by remember { mutableStateOf(false) }
    var newBudgetInput by remember { mutableStateOf("") }

    val monthNames = listOf(
        "Siječanj", "Veljača", "Ožujak", "Travanj", "Svibanj", "Lipanj",
        "Srpanj", "Kolovoz", "Rujan", "Listopad", "Studeni", "Prosinac"
    )
    val monthName = monthNames[LocalDate.now().monthValue - 1]

    LaunchedEffect(Unit) {
        viewModel.loadStatistics()
    }

    LaunchedEffect(updateSuccess) {
        if (updateSuccess) {
            showEditDialog = false
            viewModel.resetUpdateSuccess()
            viewModel.loadStatistics()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {


        Text(
            "Mjesečna statistika - $monthName",
            style = MaterialTheme.typography.headlineSmall
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Mjesečni budžet: %.2f €".format(monthlyBudget))
                Text("Potrošeno: %.2f €".format(monthlySpending))

                val remaining = monthlyBudget - monthlySpending

                Text(
                    text = if (remaining >= 0)
                        "Preostalo: %.2f €".format(remaining)
                    else
                        "Prekoračeno: %.2f €".format(-remaining),
                    color = if (remaining >= 0)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error
                )

                Button(
                    onClick = {
                        newBudgetInput = monthlyBudget.toString()
                        showEditDialog = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Uredi budžet")
                }
            }
        }

        if (categorySummary.isNotEmpty()) {
            Text("Raspodjela potrošnje po kategorijama")

            PieChart(
                slices = categorySummary.map { (label, value) ->
                    PieSlice(
                        label = label,
                        value = value.toFloat(),
                        color = colorForCategory(label)
                    )
                },
                modifier = Modifier
                    .size(260.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                categorySummary.forEach { (label, value) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(colorForCategory(label))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("$label: %.2f €".format(value))
                    }
                }
            }
        }

        if (trendData.isNotEmpty()) {
            Text("Trend potrošnje (zadnjih 6 mjeseci)")

            ProfessionalLineChart(
                months = trendData.map { it.month },
                values = trendData.map { it.amount },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }
    }

    if (alertMessage != null) {
        AlertDialog(
            onDismissRequest = { viewModel.clearAlert() },
            title = { Text("Upozorenje") },
            text = { Text(alertMessage!!) },
            confirmButton = {
                TextButton(onClick = { viewModel.clearAlert() }) {
                    Text("OK")
                }
            }
        )
    }

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Uredi mjesečni budžet") },
            text = {
                OutlinedTextField(
                    value = newBudgetInput,
                    onValueChange = { newBudgetInput = it },
                    label = { Text("Iznos (€)") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val value = newBudgetInput.replace(",", ".").toDoubleOrNull()
                        if (value != null && value > 0) {
                            viewModel.updateMonthlyBudget(value)
                        }
                    }
                ) {
                    Text("Spremi")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Odustani")
                }
            }
        )
    }
}}

@Composable
fun colorForCategory(label: String): Color {
    val colors = listOf(
        Color(0xFFE57373),
        Color(0xFF64B5F6),
        Color(0xFF81C784),
        Color(0xFFFFD54F),
        Color(0xFFBA68C8),
        Color(0xFFFF8A65)
    )
    return colors[label.hashCode().absoluteValue % colors.size]
}