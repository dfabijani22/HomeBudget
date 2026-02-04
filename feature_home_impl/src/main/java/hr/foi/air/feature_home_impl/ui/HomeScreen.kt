package hr.foi.air.feature_home_impl.ui


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun HomeScreen(onLogout: () -> Unit, onAddExpense: () -> Unit, onViewExpenses: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Dobrodošli!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Ovo je početni ekran.")
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Odjava")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAddExpense,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Dodaj trošak")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onViewExpenses,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pregled troškova")
        }
    }

}
