package hr.foi.air.homebudgetapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hr.foi.air.homebudgetapp.HomeScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route)
    {
        composable(route = Screen.Home.route){
            HomeScreen()
        }
    }
}
