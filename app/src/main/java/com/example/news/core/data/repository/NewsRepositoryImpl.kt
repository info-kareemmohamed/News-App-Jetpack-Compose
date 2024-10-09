package com.example.news.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.news.core.data.local.NewsDao
import com.example.news.core.data.local.NewsDatabase
import com.example.news.core.data.remote.NewsApi
import com.example.news.core.data.NewsRemoteMediator
import com.example.news.core.domain.model.Article
import com.example.news.core.domain.model.SourceType
import com.example.news.core.domain.repository.NewsRepository
import com.example.news.core.util.Constants.PAGE_SIZE
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
            config = PagingConfig(PAGE_SIZE),
            remoteMediator = NewsRemoteMediator(
                newsApiCall = {
                    newsApi.getNews(
                        sources = sources.joinToString(separator = ","),
                        page = it
                    )
                },
                sourceType = SourceType.HOME,
                newsDao = dao,
                newsDb = newsDb
            ),
            pagingSourceFactory = {
                dao.getArticles(sourceType = SourceType.HOME)
            }
        ).flow
    }


    override fun searchForNews(
        sources: List<String>,
        searchQuery: String
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = NewsRemoteMediator(
                newsApiCall = {
                    newsApi.searchForNews(
                        searchQuery = searchQuery,
                        sources = sources.joinToString(separator = ","),
                        page = it
                    )
                },
                sourceType = SourceType.SEARCH,
                newsDao = dao,
                newsDb = newsDb
            ),
            pagingSourceFactory = {
                dao.getArticles(sourceType = SourceType.SEARCH)
            }
        ).flow
    }

    override suspend fun getArticles(sourceType: SourceType): PagingSource<Int, Article> =
        dao.getArticles(sourceType)

    override suspend fun clearArticles(sourceType: SourceType) = dao.clearArticles(sourceType)

    override suspend fun getNotBookMarkedArticles(): List<Article> = dao.getNotBookMarkedArticles()

    override suspend fun upsert(article: Article) = dao.upsert(article)

    override suspend fun upsert(articles: List<Article>) = dao.upsert(articles)


    override suspend fun delete(article: Article) = dao.delete(article)

    override suspend fun getArticleByUrl(url: String): Article? = dao.getArticle(url)

    override fun getBookMarkedArticles(): Flow<List<Article>> = dao.getBookMarkedArticles()

    override fun getBookMarkedArticlesForBookmark(sourceType: SourceType): Flow<List<Article>> =
        dao.getBookMarkedArticlesForBookmark(sourceType)

    override suspend fun updateBookmarkStatus(
        url: String,
        isBookMarked: Boolean,
        sourceType: SourceType
    ) =
        dao.updateBookmarkStatus(url, isBookMarked, sourceType)


}