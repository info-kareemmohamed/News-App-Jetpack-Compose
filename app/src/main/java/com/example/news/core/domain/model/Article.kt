package com.example.news.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["url", "sourceType"])
data class Article(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val sourceName: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val sourceType: SourceType,
    val isBookMarked: Boolean = false
)

/**
 * This enum class `SourceType` is used to handle data separation between different sources like
 * HOME, SEARCH, and BOOKMARK. It's created to prevent data from overlapping between these sections.
 * Without this separation, data from one source (e.g., search results) could unintentionally
 * show up in another section (e.g., home screen). By using `SourceType`, we ensure that each data
 * source (HOME, SEARCH, BOOKMARK) remains distinct, so that bookmark data, for example, doesn't
 * mix with home data.
 */

enum class SourceType {
    HOME, SEARCH, BOOKMARK
}