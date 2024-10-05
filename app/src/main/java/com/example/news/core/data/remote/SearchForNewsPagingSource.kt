package com.example.news.core.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.news.core.data.mappers.toArticle
import com.example.news.core.domain.model.Article
import javax.inject.Inject

class SearchForNewsPagingSource @Inject constructor(
    private val newsApi: NewsApi,
    private val searchQuery: String,
    private val sources: String
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            return state.anchorPosition?.let { position ->
                state.closestPageToPosition(position)?.nextKey?.minus(1) ?: 1
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val newsResponse = newsApi.searchForNews(searchQuery, sources, page)
            val articles = newsResponse.articles.map { it.toArticle() }
            LoadResult.Page(
                data = articles,
                nextKey = if (newsResponse.articles.isNotEmpty()) page + 1 else null,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}