package com.example.news.home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.news.home.data.remote.NewsApi
import com.example.news.home.data.remote.NewsPagingSource
import com.example.news.home.domain.model.Article
import com.example.news.home.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(newsApi, sources.joinToString(separator = ","))
            }
        ).flow
    }
}