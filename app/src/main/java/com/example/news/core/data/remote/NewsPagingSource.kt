package com.example.news.core.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.news.core.data.mappers.toArticle
import com.example.news.core.domain.model.Article


class NewsPagingSource(
    private val newsApi: NewsApi,
    private val sources: String
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.nextKey?.minus(1) ?: 1
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val newsResponse = newsApi.getNews(sources, page)
            val articles = newsResponse.articles.map { it.toArticle() }
            LoadResult.Page(
                data = articles,
                nextKey = if ((newsResponse.totalResults / page) == 0) null else page + 1,
                prevKey = if (page == 1) null else page - 1
            )

        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }


}