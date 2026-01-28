package hr.foi.air.homebudgetapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import hr.foi.air.feature_home_impl.ui.HOME_ROUTE
import hr.foi.air.feature_home_impl.ui.auth.REGISTER_ROUTE
import hr.foi.air.feature_home_impl.ui.auth.LOGIN_ROUTE
import hr.foi.air.feature_home_impl.ui.auth.loginNav
import hr.foi.air.feature_home_impl.ui.auth.registerNav
import hr.foi.air.feature_home_impl.ui.homeNav

@Composable
fun NavigationGraph(
    navController: NavHostController,
    isLoggedIn: Boolean,
    onRegisterSuccess: () -> Unit,
    onLoginSuccess: () -> Unit

) {
    val startDestination = if (isLoggedIn) HOME_ROUTE else LOGIN_ROUTE

    NavHost(navController = navController, startDestination = startDestination) {
            loginNav(
                onLoginSuccess = {
                    onLoginSuccess()
                    navController.navigate(HOME_ROUTE) {
                        popUpTo(LOGIN_ROUTE) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(REGISTER_ROUTE) {
                        launchSingleTop = true
                    }
                })
        registerNav (onRegisterSuccess = {
            onRegisterSuccess()
            navController.navigate(LOGIN_ROUTE) {
                popUpTo(REGISTER_ROUTE) { inclusive = true }
                launchSingleTop = true
            }
        },
            onNavigateToLogin = {
                navController.navigate(LOGIN_ROUTE) {
                    launchSingleTop = true
                }
            })
        homeNav()
    }
}
