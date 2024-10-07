package com.example.news.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.news.core.data.local.NewsDao
import com.example.news.core.data.remote.NewsApi
import com.example.news.core.data.remote.NewsPagingSource
import com.example.news.core.data.remote.SearchForNewsPagingSource
import com.example.news.core.domain.model.Article
import com.example.news.core.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val dao: NewsDao
) : NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 15),
            pagingSourceFactory = {
                NewsPagingSource(newsApi, sources.joinToString(separator = ","))
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

    override suspend fun upsert(article: Article) = dao.upsert(article)

    override suspend fun delete(article: Article) = dao.delete(article)

    override suspend fun getArticleByUrl(url: String): Article? = dao.getArticle(url)

    override fun getBookMarkedArticles(): Flow<List<Article>> = dao.getBookMarkedArticles()


}