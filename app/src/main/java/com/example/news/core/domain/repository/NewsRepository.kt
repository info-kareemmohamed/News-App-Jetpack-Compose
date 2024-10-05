package com.example.news.core.domain.repository

import androidx.paging.PagingData
import com.example.news.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
     fun getNews(sources: List<String>): Flow<PagingData<Article>>
}