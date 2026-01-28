package hr.foi.air.homebudgetapp.navigation
sealed class Screen(val route: String) {
    object Register : Screen("register")
    object Home : Screen("home")
}
