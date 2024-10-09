package com.example.news.core.data.mappers

import com.example.news.core.data.remote.dto.ArticleDto
import com.example.news.core.domain.model.Article
import com.example.news.core.domain.model.SourceType

fun ArticleDto.toArticle(isBookMarked: Boolean,sourceType: SourceType ): Article =
    Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceName = source.name,
        title = title,
        url = url,
        urlToImage =  urlToImage,
        sourceType = sourceType,
        isBookMarked = isBookMarked
    )

