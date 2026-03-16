package hr.foi.air.feature_home_impl.ui.category

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hr.foi.air.feature_home_impl.viewModel.category.UpdateCategoryViewModel

@Composable
fun UpdateCategoryScreen(
    id: Int,
    viewModel: UpdateCategoryViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val category by viewModel.category.collectAsState()
    val updateSuccess by viewModel.updateSuccess.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val deleteSuccess by viewModel.deleteSuccess.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadCategory(id)
    }

    LaunchedEffect(updateSuccess) {
        if (updateSuccess) {
            onBack()
            viewModel.clearMessages()
        }
    }

    LaunchedEffect(deleteSuccess) {
        if (deleteSuccess) {
            onBack()
            viewModel.onDeleteHandled()
        }
    }

    if (category == null) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val isSystem = category!!.isDefault == true


    var name by remember(category) { mutableStateOf(category?.name ?: "") }
    var description by remember(category) { mutableStateOf(category?.description ?: "")}
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (isSystem) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Ova kategorija je sistemska i nije moguće uređivanje.",
                style = MaterialTheme.typography.titleMedium
            )
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Uredi kategoriju", style = MaterialTheme.typography.titleLarge)

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error
            )
        }

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Naziv kategorije") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Opis kategorije (opcionalno)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.updateCategory(
                    id = id,
                    name = name.trim(),
                    description = description.trim(),
                    isDefault = category!!.isDefault
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Spremi promjene")
        }

        Button(
            onClick = { showDeleteDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Obriši kategoriju")
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Brisanje kategorije") },
            text = { Text("Jesi sigurna da želiš obrisati ovu kategoriju?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteCategory(id)
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