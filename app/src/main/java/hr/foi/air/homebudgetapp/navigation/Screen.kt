package hr.foi.air.homebudgetapp.navigation
sealed class Screen(val route: String) {
    object Home : Screen("home")
}
