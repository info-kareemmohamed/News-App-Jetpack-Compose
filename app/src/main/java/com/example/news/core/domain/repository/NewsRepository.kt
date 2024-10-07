package com.example.news.core.domain.repository

import androidx.paging.PagingData
import com.example.news.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>

    fun searchForNews(sources: List<String>, searchQuery: String): Flow<PagingData<Article>>

    suspend fun upsert(article: Article)

    suspend fun delete(article: Article)

    suspend fun getArticleByUrl(url: String): Article?

    fun getBookMarkedArticles(): Flow<List<Article>>
}