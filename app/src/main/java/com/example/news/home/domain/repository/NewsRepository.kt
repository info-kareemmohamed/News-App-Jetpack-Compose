package com.example.news.home.domain.repository

import androidx.paging.PagingData
import com.example.news.home.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
     fun getNews(sources: List<String>): Flow<PagingData<Article>>
}