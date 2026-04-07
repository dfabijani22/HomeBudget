package hr.foi.air.feature_home_impl.ui.category

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hr.foi.air.feature_home_impl.viewModel.category.AddCategoryViewModel

@Composable
fun NewCategoryScreen(
    viewModel: AddCategoryViewModel = hiltViewModel(),
    onCategoryAdded: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val categoryAdded by viewModel.categoryAdded.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(categoryAdded) {
        if (categoryAdded) {
            Toast.makeText(context, "Kategorija uspješno dodana!", Toast.LENGTH_LONG).show()
            viewModel.resetState()
            onCategoryAdded()
        }
    }

    LaunchedEffect(error) {
        if (error != null) {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            viewModel.clearError()
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
            label = { Text("Naziv kategorije") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Opis kategorije (opcionalno)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isBlank()) {
                    Toast.makeText(context, "Naziv je obavezan.", Toast.LENGTH_LONG).show()
                    return@Button
                }

                viewModel.addCategory(
                    name = name,
                    description = description,
                    isDefault = false
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Spremi kategoriju")
        }

        if (isLoading) {
            Spacer(Modifier.height(12.dp))
            CircularProgressIndicator()
        }
    }
}