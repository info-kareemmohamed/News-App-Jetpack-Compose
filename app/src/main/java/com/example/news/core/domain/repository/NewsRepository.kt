package com.example.news.core.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.news.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>

    suspend fun getArticles(): PagingSource<Int, Article>

    suspend fun getNotBookMarkedArticles(): List<Article>

    suspend fun clearArticlesIsNotBookMarked()

    fun searchForNews(sources: List<String>, searchQuery: String): Flow<PagingData<Article>>

    suspend fun upsert(article: Article)

    suspend fun upsert(articles: List<Article>)

    suspend fun delete(article: Article)

    suspend fun getArticleByUrl(url: String): Article?

    fun getBookMarkedArticles(): Flow<List<Article>>

    suspend fun updateBookmarkStatus(url: String,isBookMarked: Boolean)

}