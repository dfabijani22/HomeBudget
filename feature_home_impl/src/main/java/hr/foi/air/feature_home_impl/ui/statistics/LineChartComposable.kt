package hr.foi.air.feature_home_impl.ui.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun ProfessionalLineChart(
    months: List<String>,
    values: List<Double>,
    modifier: Modifier = Modifier,
    lineColor: Color = Color(0xFF3F51B5),
) {

    Canvas(modifier = modifier) {

        if (values.isEmpty()) return@Canvas

        val count = values.size
        val max = values.maxOrNull() ?: 0.0
        val min = 0.0   // uvijek od 0

        val leftPadding = 80f
        val bottomPadding = 50f
        val topPadding = 40f

        val chartWidth = size.width - leftPadding
        val chartHeight = size.height - bottomPadding - topPadding

        val steps = 4
        val stepValue = (max / steps).coerceAtLeast(1.0)

        for (i in 0..steps) {
            val y = size.height - bottomPadding - (i * (chartHeight / steps))

            drawLine(
                color = Color(0xFFE0E0E0),
                start = Offset(leftPadding, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )

            // value labels
            drawContext.canvas.nativeCanvas.drawText(
                "${(i * stepValue).toInt()} €",
                10f,
                y + 10f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.DKGRAY
                    textSize = 30f
                }
            )
        }


        val stepX = chartWidth / (count - 1).coerceAtLeast(1)

        val points = values.mapIndexed { i, v ->
            val x = leftPadding + i * stepX
            val y = size.height - bottomPadding - ((v - min) / (max - min).coerceAtLeast(1.0) * chartHeight).toFloat()
            Offset(x, y)
        }

        val path = Path().apply {
            moveTo(points.first().x, points.first().y)
            for (i in 1 until points.size) {
                lineTo(points[i].x, points[i].y)
            }
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 6f, cap = StrokeCap.Round)
        )

        points.forEach {
            drawCircle(
                color = lineColor,
                radius = 8f,
                center = it
            )
        }

        months.forEachIndexed { i, month ->
            val x = leftPadding + i * stepX
            val y = size.height - 10f

            drawContext.canvas.nativeCanvas.drawText(
                month,
                x - 20,
                y,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.DKGRAY
                    textSize = 32f
                }
            )
        }
    }
}
