package com.example.news.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.news.core.data.local.NewsDao
import com.example.news.core.data.local.NewsDatabase
import com.example.news.core.data.remote.NewsApi
import com.example.news.core.data.remote.NewsRemoteMediator
import com.example.news.core.data.remote.SearchForNewsPagingSource
import com.example.news.core.domain.model.Article
import com.example.news.core.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDb: NewsDatabase,
    private val dao: NewsDao
) : NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 15),
            remoteMediator = NewsRemoteMediator(
                newsApi = newsApi,
                newsDao = dao,
                sources = sources.joinToString(separator = ","),
                newsDb = newsDb
            ),
            pagingSourceFactory = {
                dao.getArticles()
            }
        ).flow
    }


    override fun searchForNews(
        sources: List<String>,
        searchQuery: String
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(15),
            pagingSourceFactory = {
                SearchForNewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ","),
                    searchQuery = searchQuery
                )
            }
        ).flow

    }

    override suspend fun getArticles(): PagingSource<Int, Article> = dao.getArticles()

    override suspend fun clearArticlesIsNotBookMarked() = dao.clearArticlesIsNotBookMarked()

    override suspend fun getNotBookMarkedArticles(): List<Article> = dao.getNotBookMarkedArticles()

    override suspend fun upsert(article: Article) = dao.upsert(article)

    override suspend fun upsert(articles: List<Article>) = dao.upsert(articles)


    override suspend fun delete(article: Article) = dao.delete(article)

    override suspend fun getArticleByUrl(url: String): Article? = dao.getArticle(url)

    override fun getBookMarkedArticles(): Flow<List<Article>> = dao.getBookMarkedArticles()

    override suspend fun updateBookmarkStatus(url: String, isBookMarked: Boolean) =
        dao.updateBookmarkStatus(url, isBookMarked)


}