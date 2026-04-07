package hr.foi.air.feature_home_impl.viewModel.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.feature_home_api.StatisticsRepository
import hr.foi.feature_home_api.api.ExpenseRepository
import hr.foi.feature_home_api.model.MonthlyBudgetRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class MonthlyTrendPoint(val month: String, val amount: Double)

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository,
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _monthlyBudget = MutableStateFlow(0.0)
    val monthlyBudget = _monthlyBudget.asStateFlow()

    private val _monthlySpending = MutableStateFlow(0.0)
    val monthlySpending = _monthlySpending.asStateFlow()

    private val _alertMessage = MutableStateFlow<String?>(null)
    val alertMessage = _alertMessage.asStateFlow()

    private val _updateSuccess = MutableStateFlow(false)
    val updateSuccess = _updateSuccess.asStateFlow()

    // Pie chart summary
    private val _categorySummary =
        MutableStateFlow<List<Pair<String, Double>>>(emptyList())
    val categorySummary = _categorySummary.asStateFlow()

    // Trend chart summary
    private val _trend =
        MutableStateFlow<List<MonthlyTrendPoint>>(emptyList())
    val trend = _trend.asStateFlow()

    fun loadStatistics() {
        val now = LocalDate.now()
        val year = now.year
        val month = now.monthValue

        viewModelScope.launch {

            val budgetRes = statisticsRepository.getMonthlyBudget(
                year = year,
                month = month
            )
            val budget = budgetRes.data?.amount ?: 0.0
            _monthlyBudget.value = budget

            val expensesRes = expenseRepository.getExpenses(
                year = year,
                month = month,
                categoryId = null
            )
            val expenses = expensesRes.data ?: emptyList()
            val spending = expenses.sumOf { it.amount }
            _monthlySpending.value = spending

            _alertMessage.value = when {
                budget == 0.0 -> null
                spending > budget -> "Mjesečni budžet je premašen!"
                spending >= budget * 0.80 -> "Dosegli ste 80% mjesečnog budžeta!"
                else -> null
            }

            val summary = expenses
                .groupBy { it.categoryName }
                .map { (cat, list) -> cat to list.sumOf { it.amount } }
            _categorySummary.value = summary

            loadTrend()
        }
    }

    private fun loadTrend() {
        viewModelScope.launch {
            val now = LocalDate.now()
            val lastSix = (0..5).map {
                now.minusMonths(it.toLong())
            }.reversed()

            val trendList = mutableListOf<MonthlyTrendPoint>()

            for (date in lastSix) {
                val res = expenseRepository.getExpenses(
                    year = date.year,
                    month = date.monthValue,
                    categoryId = null
                )
                val sum = res.data?.sumOf { it.amount } ?: 0.0
                trendList.add(
                    MonthlyTrendPoint(
                        month = shortMonthName(date.monthValue),
                        amount = sum
                    )
                )
            }

            _trend.value = trendList
        }
    }

    private fun shortMonthName(m: Int): String {
        val names = listOf(
            "Sij", "Velj", "Ožu", "Tra", "Svi", "Lip",
            "Srp", "Kol", "Ruj", "Lis", "Stu", "Pro"
        )
        return names[m - 1]
    }

    fun updateMonthlyBudget(value: Double) {
        val now = LocalDate.now()
        viewModelScope.launch {
            val request = MonthlyBudgetRequest(
                year = now.year,
                month = now.monthValue,
                amount = value
            )
            val res = statisticsRepository.setMonthlyBudget(request)
            if (res.success) _updateSuccess.value = true
        }
    }

    fun resetUpdateSuccess() {
        _updateSuccess.value = false
    }

    fun clearAlert() {
        _alertMessage.value = null
    }
}