package hr.foi.air.feature_home_impl.ui.statistics

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hr.foi.air.feature_home_impl.ui.STATISTICS_ROUTE

fun NavGraphBuilder.statisticsNav(
) {
    composable(STATISTICS_ROUTE) {
        StatisticsScreen()
    }
}