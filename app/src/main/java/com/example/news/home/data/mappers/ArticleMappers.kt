package com.example.news.home.data.mappers

import com.example.news.home.data.remote.dto.ArticleDto
import com.example.news.home.domain.model.Article

fun ArticleDto.toArticle():Article=
    Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceName = source.name,
        title = title,
        url = url,
        urlToImage =  urlToImage
    )

