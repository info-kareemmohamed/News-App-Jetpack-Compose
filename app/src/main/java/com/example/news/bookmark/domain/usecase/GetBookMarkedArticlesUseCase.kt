package com.example.news.bookmark.domain.usecase


import com.example.news.core.domain.model.Article
import com.example.news.core.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookMarkedArticlesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<List<Article>> = newsRepository.getBookMarkedArticles()

}