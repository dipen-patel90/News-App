package com.ct.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    fun String.toDate(): Date? {
        val UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val format: DateFormat = SimpleDateFormat(UTC_FORMAT, Locale.ENGLISH)
        return format.parse(this)
    }
}