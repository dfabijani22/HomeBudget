package hr.foi.air.feature_home_impl.ui.expense


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hr.foi.air.core.network.data.CategoryData
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

    LaunchedEffect(categoryAdded) {
        if (categoryAdded) {
            Toast.makeText(context, "Kategorija uspješno dodana!", Toast.LENGTH_LONG).show()
            viewModel.onSnackShown()
            onCategoryAdded()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Naziv") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Naziv") },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                if (name.isBlank() || description.isBlank()) {
                    Toast.makeText(context, "Molimo ispunite sva polja ispravno.", Toast.LENGTH_LONG).show()
                    return@Button
                }

                val category = CategoryData(
                    name = name,
                    description = description
                )

                viewModel.addCategory(category)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Spremi kategoriju")
        }
    }
}
