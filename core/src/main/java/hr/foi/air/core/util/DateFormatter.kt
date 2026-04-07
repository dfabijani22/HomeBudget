package hr.foi.air.core.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatter {

    private val inputFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    private val outputFormat =
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    fun format(date: String): String {
        return try {
            val parsed = inputFormat.parse(date)
            outputFormat.format(parsed!!)
        } catch (e: Exception) {
            date
        }
    }
}