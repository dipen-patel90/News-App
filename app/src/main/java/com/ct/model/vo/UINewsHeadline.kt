package com.ct.model.vo

import java.util.Date

data class UINewsHeadline(
    val title: String,
    val imageUrl: String?,
    val publishedAt: Date?,
    val description: String,
    val content: String,
    var isSelected: Boolean = false,
) {
    override fun toString(): String {
        return super.toString()
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}