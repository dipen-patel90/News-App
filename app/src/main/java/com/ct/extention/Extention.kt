package com.ct.extention

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.addVerticalIndicator() {
    addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
}

fun RecyclerView.addHorizontalIndicator() {
    addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.HORIZONTAL))
}