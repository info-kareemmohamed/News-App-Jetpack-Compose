package com.example.news.core.data.mappers

import com.example.news.core.data.remote.dto.ArticleDto
import com.example.news.core.domain.model.Article

fun ArticleDto.toArticle(isBookMarked: Boolean ): Article =
    Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceName = source.name,
        title = title,
        url = url,
        urlToImage =  urlToImage,
        isBookMarked = isBookMarked
    )

