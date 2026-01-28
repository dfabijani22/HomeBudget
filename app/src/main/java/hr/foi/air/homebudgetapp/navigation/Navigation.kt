package hr.foi.air.homebudgetapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import hr.foi.air.feature_home_impl.ui.HOME_ROUTE
import hr.foi.air.feature_home_impl.ui.auth.REGISTER_ROUTE
import hr.foi.air.feature_home_impl.ui.auth.registerNav
import hr.foi.air.feature_home_impl.ui.homeNav

@Composable
fun NavigationGraph(
    navController: NavHostController,
    isLoggedIn: Boolean,
    onRegisterSuccess: () -> Unit
) {
    val startDestination = if (isLoggedIn) HOME_ROUTE else REGISTER_ROUTE

    NavHost(navController = navController, startDestination = startDestination) {
        registerNav {
            onRegisterSuccess()
            navController.navigate(HOME_ROUTE) {
                popUpTo(REGISTER_ROUTE) { inclusive = true }
                launchSingleTop = true
            }
        }
        homeNav()
    }
}
