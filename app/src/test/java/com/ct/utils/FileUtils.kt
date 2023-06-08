package com.ct.utils

import java.io.InputStreamReader

object FileUtils {

    fun readFile(filepath: String): String {
        val inputStream = javaClass.getResourceAsStream(filepath)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}