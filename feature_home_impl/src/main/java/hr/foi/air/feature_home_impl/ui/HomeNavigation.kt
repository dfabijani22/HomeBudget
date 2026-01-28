package hr.foi.air.feature_home_impl.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeNav() {
    composable(route = HOME_ROUTE) {
        HomeScreen()
    }
}
