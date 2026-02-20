package hr.foi.air.feature_home_impl.ui.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hr.foi.air.feature_home_impl.viewModel.category.CategoriesViewModel

@Composable
fun CategoriesListScreen (
    viewModel: CategoriesViewModel = hiltViewModel(),
    onUpdateCategory: (Int) -> Unit
) {
    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(categories) {
        viewModel.loadCategories()
    }

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(categories) { category ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Naziv: ${category.name}", fontWeight = FontWeight.Bold)

                            IconButton(onClick = { onUpdateCategory(category.id!!) }) {
                                Icon(Icons.Default.Edit, contentDescription = null)
                            }

                            Text("Opis: ${category.description}")
                        }
                    }
                }
            }
        }
    }
