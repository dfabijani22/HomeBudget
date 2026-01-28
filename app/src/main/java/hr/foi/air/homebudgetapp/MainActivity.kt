package hr.foi.air.homebudgetapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hr.foi.air.homebudgetapp.navigation.NavigationGraph
import hr.foi.air.homebudgetapp.ui.theme.HomeBudgetAppTheme
import kotlinx.coroutines.launch
import javax.inject.Inject
import hr.foi.air.core.network.data.UserDataStore

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userDataStore: UserDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            userDataStore.setIsLoggedIn(false)
        }

        setContent {
            val navController = rememberNavController()
            val isLoggedInState = remember { mutableStateOf<Boolean?>(null) }

            LaunchedEffect(Unit) {
                userDataStore.isLoggedIn.collect {
                    isLoggedInState.value = it
                }
            }

            isLoggedInState.value?.let { isLoggedIn ->
                HomeBudgetAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavigationGraph(
                            navController = navController,
                            isLoggedIn = isLoggedIn,
                            onRegisterSuccess = {
                                // Zapiši login status
                                lifecycleScope.launch {
                                    userDataStore.setIsLoggedIn(true)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
