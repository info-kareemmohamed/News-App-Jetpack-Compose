package com.example.news.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.news.core.data.remote.NewsApi
import com.example.news.core.data.remote.NewsPagingSource
import com.example.news.core.data.remote.SearchForNewsPagingSource
import com.example.news.core.domain.model.Article
import com.example.news.core.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
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
}