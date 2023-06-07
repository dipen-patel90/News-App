package com.ct.utils

import com.ct.Config
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    fun String.toDate(): Date? {
        val format: DateFormat = SimpleDateFormat(Config.DATE_FORMAT, Locale.ENGLISH)
        return format.parse(this)
    }
}