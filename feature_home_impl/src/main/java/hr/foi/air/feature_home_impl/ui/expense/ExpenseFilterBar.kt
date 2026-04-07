package hr.foi.air.feature_home_impl.ui.expense

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseFilterBar(
    categories: List<Pair<Int, String>>,
    selectedMonth: Int,
    selectedCategory: Int,
    onMonthChange: (Int) -> Unit,
    onCategoryChange: (Int) -> Unit
) {

    val monthNames = listOf(
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

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DropdownMenuBox(
            label = "Mjesec",
            options = monthNames,
            selected = selectedMonth,
            onSelected = onMonthChange,
            modifier = Modifier.weight(1f)
        )

        DropdownMenuBox(
            label = "Kategorija",
            options = categories,
            selected = selectedCategory,
            onSelected = onCategoryChange,
            modifier = Modifier.weight(1f)
        )
    }
}