package com.example.news.bookmark.data.repository

import com.example.news.bookmark.data.local.ArticleBookmarkDao
import com.example.news.bookmark.domain.repository.ArticleBookmarkRepository
import com.example.news.core.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleBookmarkRepositoryImpl @Inject constructor(
    private val dao: ArticleBookmarkDao
) : ArticleBookmarkRepository {
    override suspend fun upsert(article: Article) = dao.upsert(article)

    override suspend fun delete(article: Article) = dao.delete(article)

    override fun getArticles(): Flow<List<Article>> = dao.getArticles()

    override suspend fun getArticle(url: String): Article? = dao.getArticle(url)
}