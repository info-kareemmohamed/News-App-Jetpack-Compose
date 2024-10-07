package com.example.news.core.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.news.core.data.local.NewsDao
import com.example.news.core.data.local.NewsDatabase
import com.example.news.core.data.mappers.toArticle
import com.example.news.core.domain.model.Article


@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao,
    private val newsDb: NewsDatabase,
    private val sources: String
) : RemoteMediator<Int, Article>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1  // Reset to first page on refresh
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    state.pages.size + 1  // Increment page number
                }
            }

            // Fetch data from API and database
            val newsResponse = newsApi.getNews(sources, page)
            val bookMarkedArticles = newsDao.getNotBookMarkedArticles() // Get only once

            newsDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsDao.clearArticlesIsNotBookMarked() // Clear old non-bookmarked articles
                }

                val articles = newsResponse.articles.map { article ->
                    article.toArticle().copy(
                        isBookMarked = bookMarkedArticles.any { it.url == article.url } // Set bookmark status
                    )
                }

                // Insert new articles into the database
                newsDao.upsert(articles)
            }

            // Return success with pagination state
            MediatorResult.Success(endOfPaginationReached = newsResponse.articles.isEmpty())
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }


}