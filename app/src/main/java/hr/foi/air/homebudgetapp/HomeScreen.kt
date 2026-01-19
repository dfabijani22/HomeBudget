package hr.foi.air.homebudgetapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val message by viewModel.message.collectAsState()

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = message, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.refreshMessage() }) {
            Text("Osvježi poruku")
        }
    }
}
