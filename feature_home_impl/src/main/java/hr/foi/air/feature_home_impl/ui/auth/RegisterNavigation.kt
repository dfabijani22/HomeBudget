package hr.foi.air.feature_home_impl.ui.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val REGISTER_ROUTE = "register"

fun NavGraphBuilder.registerNav(onRegisterSuccess: () -> Unit) {
    composable(route = REGISTER_ROUTE) {
        RegisterScreen(onRegisterSuccess = onRegisterSuccess)
    }
}
