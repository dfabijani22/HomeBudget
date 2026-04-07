package hr.foi.air.feature_home_impl.ui.statistics


import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
@Composable
fun PieChart(
    slices: List<PieSlice>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {

        val total = slices.sumOf { it.value.toDouble() }.toFloat()
        var startAngle = -90f

        slices.forEach { slice ->
            val sweep = (slice.value / total) * 360f

            drawArc(
                color = slice.color,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = true
            )

            startAngle += sweep
        }
    }
}
