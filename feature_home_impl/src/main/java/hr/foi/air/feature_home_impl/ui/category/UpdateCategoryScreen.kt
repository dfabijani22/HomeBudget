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

    // UCITAVANJE KATEGORIJE
    LaunchedEffect(id) {
        viewModel.loadCategory(id)
    }

    // NAKON USPJEHA VRATI SE NAZAD
    LaunchedEffect(updateSuccess) {
        if (updateSuccess) {
            onBack()
            viewModel.clearMessages()
        }
    }

    // LOADER
    if (category == null) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val isSystem = category!!.isDefault == true   // PROVJERA SISTEMSKE

    // STATEOVI ZA FORME
    var name by remember { mutableStateOf(category!!.name) }
    var description by remember { mutableStateOf(category!!.description ?: "") }

    // AKO JE SISTEMSKA → NE DOPUSTAJ EDIT
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

    // UI ZA EDIT KATEGORIJE
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Uredi kategoriju", style = MaterialTheme.typography.titleLarge)

        // GREŠKA SA BACKENDA (duplikat, invalid, itd.)
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
                    description = description.trim()
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Spremi promjene")
        }
    }
}