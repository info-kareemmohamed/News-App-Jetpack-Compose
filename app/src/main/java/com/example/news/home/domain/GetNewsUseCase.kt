package com.example.news.home.domain

import com.example.news.core.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
     operator fun invoke(sources: List<String>) = newsRepository.getNews(sources)

}