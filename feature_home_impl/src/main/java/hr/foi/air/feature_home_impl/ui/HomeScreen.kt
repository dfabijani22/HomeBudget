package hr.foi.air.feature_home_impl.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.air.feature_home_impl.viewModel.HomeViewModel
import androidx.compose.runtime.Composable


@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val message by viewModel.message.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMessage()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.fetchMessage() }) {
            Text("Osvježi poruku")
        }
    }
}
