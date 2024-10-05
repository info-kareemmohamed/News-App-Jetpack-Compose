package com.example.news.bookmark.domain.repository

import com.example.news.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleBookMarkRepository {

    suspend fun upsert(article: Article)

    suspend fun delete(article: Article)

    fun getArticles(): Flow<List<Article>>

    suspend fun getArticle(url: String): Article?
}