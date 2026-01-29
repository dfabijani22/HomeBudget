package hr.foi.air.feature_home_impl.ui.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController

const val LOGIN_ROUTE = "login"

fun NavGraphBuilder.loginNav(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    composable(LOGIN_ROUTE) {
        LoginScreen(onLoginSuccess = onLoginSuccess, onNavigateToRegister = onNavigateToRegister)
    }
}
