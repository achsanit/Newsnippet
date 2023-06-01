package com.achsanit.newsnippet.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {

    fun formatTimeIso86012(date: String): String {
        return try {
            val df1: DateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val result: Date = df1.parse(date)
            val format = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
            format.format(result)
        } catch (e: Exception) {
            e.message ?: "Error"
        }
    }
}