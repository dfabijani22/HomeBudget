package hr.foi.air.feature_home_impl.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.unit.dp
import hr.foi.air.feature_home_impl.viewModel.auth.RegisterViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit
) {
    val viewModel: RegisterViewModel = hiltViewModel()

    val registerData by viewModel.registerData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val success by viewModel.success.collectAsState()

    LaunchedEffect(success) {
        if (success) {
            onRegisterSuccess()
            viewModel.resetSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Registracija", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registerData.firstName,
            onValueChange = { viewModel.onFieldChange(registerData.copy(firstName = it)) },
            label = { Text("Ime") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = registerData.lastName,
            onValueChange = { viewModel.onFieldChange(registerData.copy(lastName = it)) },
            label = { Text("Prezime") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = registerData.email,
            onValueChange = { viewModel.onFieldChange(registerData.copy(email = it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = registerData.password,
            onValueChange = { viewModel.onFieldChange(registerData.copy(password = it)) },
            label = { Text("Lozinka") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = registerData.confirmPassword,
            onValueChange = { viewModel.onFieldChange(registerData.copy(confirmPassword = it)) },
            label = { Text("Potvrdi lozinku") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = { viewModel.registerUser() },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) "Registriram..." else "Registriraj se")
        }
    }
}
