package com.example.news.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.news.core.data.local.NewsDao
import com.example.news.core.data.local.NewsDatabase
import com.example.news.core.data.mappers.toArticle
import com.example.news.core.data.remote.dto.NewsResponse
import com.example.news.core.domain.model.Article
import com.example.news.core.domain.model.SourceType
import kotlinx.coroutines.flow.first

/**
 * `NewsRemoteMediator` implements the Single Source of Truth (SSOT) pattern.
 *
 * Purpose:
 * - To efficiently manage and sync data between the local database and the remote API.
 * - This mediator accepts a flexible API call `newsApiCall` as a function (`newsApiCall: suspend (Int) -> NewsResponse`),
 *   allowing it to handle multiple API endpoints without duplicating logic.
 * - The `sourceType` is used to keep data from different contexts (e.g., HOME, SEARCH, BOOKMARK) isolated, ensuring
 *   that cached data is handled properly per screen and does not overlap.
 *
 * Main Functionality:
 * - It fetches the correct page from the API, determines if it should refresh, prepend, or append data,
 *   and updates the local database accordingly.
 * - It ensures that bookmarked articles are not overwritten during caching by checking against the
 *   database-stored bookmarks.
 *
 * Transaction Logic:
 * - When loading a page (or refreshing), it clears old data specific to the `sourceType` before updating the cache.
 * - It updates the articles in the database by setting the bookmark status based on the existing bookmarks.
 */

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsApiCall: suspend (Int) -> NewsResponse,  // Flexible API call for any data source
    private val newsDao: NewsDao,  // Data Access Object for handling local database interactions
    private val newsDb: NewsDatabase,  // Database instance to handle transactions
    private val sourceType: SourceType  // The type of source (HOME, SEARCH, BOOKMARK) to separate data
) : RemoteMediator<Int, Article>() {

    override suspend fun load(
        loadType: LoadType,  // Defines whether to refresh, prepend, or append data
        state: PagingState<Int, Article>  // Tracks pagination state for efficient loading
    ): MediatorResult {
        return try {
            // Determine the correct page to load based on load type
            val page = when (loadType) {
                LoadType.REFRESH -> 1  // Reset to the first page if it's a refresh
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)  // No prepend for this API
                LoadType.APPEND -> state.lastItemOrNull()?.let { state.pages.size + 1 }
                    ?: return MediatorResult.Success(endOfPaginationReached = true)  // Append logic
            }

            // Fetch new data from the API
            val newsResponse = newsApiCall(page)

            // Fetch bookmarked articles from the database to maintain bookmark status
            val bookMarkedArticles = newsDao.getBookMarkedArticles().first()

            // Perform all database operations in a single transaction to ensure consistency
            newsDb.withTransaction {
                // Clear old articles for this sourceType on refresh to avoid mixing data from different sources
                if (loadType == LoadType.REFRESH) {
                    newsDao.clearArticles(sourceType = sourceType)
                }

                // Map API articles to local database models, preserving bookmark status
                val articles = newsResponse.articles.map { article ->
                    article.toArticle(
                        isBookMarked = bookMarkedArticles.any { it.url == article.url },  // Check if the article is bookmarked
                        sourceType = sourceType  // Associate the article with its source type
                    )
                }

                // Insert new articles into the database
                newsDao.upsert(articles)
            }

            // Return success and indicate if more data is available for pagination
            MediatorResult.Success(endOfPaginationReached = newsResponse.articles.isEmpty())

        } catch (e: Exception) {
            e.printStackTrace()  // Log the error for debugging purposes
            MediatorResult.Error(e)  // Return an error if the API call or database operation fails
        }
    }
}
