package com.ct.model.vo

import java.util.Date

data class UINewsHeadline(
    val title: String,
    val imageUrl: String?,
    val publishedAt: Date?,
    val description: String,
    val content: String
)