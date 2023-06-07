package com.ct.extention

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking


fun RecyclerView.addVerticalIndicator() {
    addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
}

fun RecyclerView.addHorizontalIndicator() {
    addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.HORIZONTAL))
}

fun <T> SharedFlow<T>.getValueBlockedOrNull(): T? {
    var value: T?
    runBlocking(Dispatchers.Default) {
        value = when (this@getValueBlockedOrNull.replayCache.isEmpty()) {
            true -> null
            else -> this@getValueBlockedOrNull.firstOrNull()
        }
    }
    return value
}