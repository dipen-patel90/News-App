package com.ct.model.dto.response


import com.ct.extention.empty
import com.ct.model.vo.UINewsHeadline
import com.ct.utils.DateUtils.toDate
import com.google.gson.annotations.SerializedName

data class NewsHeadline(
    @SerializedName("articles")
    val articles: List<Article>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?
) {
    data class Article(
        @SerializedName("author")
        val author: String?,
        @SerializedName("content")
        val content: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("publishedAt")
        val publishedAt: String?,
        @SerializedName("source")
        val source: Source?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("url")
        val url: String?,
        @SerializedName("urlToImage")
        val urlToImage: String?
    )

    data class Source(
        @SerializedName("id")
        val id: String?,
        @SerializedName("name")
        val name: String?
    )
}

fun NewsHeadline.Article.toUINewsHeadline(): UINewsHeadline {
    return UINewsHeadline(
        title = title ?: String.empty(),
        imageUrl = urlToImage,
        publishedAt = publishedAt?.toDate(),
        description = description ?: String.empty(),
        content = content ?: String.empty()
    )
}
