package com.ct.api

import com.ct.extention.empty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class NewsAppEnvironment(
    private val baseUrl: String,
    private var urlPath: String,
    private val pathMap: Map<String, String>? = null
) : ReadWriteProperty<Any?, String> {

    private var completeUrl: String = String.empty()

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        replacePathValues()
        return baseUrl + urlPath
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        replacePathValues()
        completeUrl = baseUrl + urlPath
    }

    private fun replacePathValues() {
        if (!pathMap.isNullOrEmpty()) {
            pathMap.keys.forEach { key ->
                pathMap[key]?.let {
                    urlPath = urlPath.replace(key, it)
                }
            }
        }
    }
}