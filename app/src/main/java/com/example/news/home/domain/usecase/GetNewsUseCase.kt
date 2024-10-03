package com.example.news.home.domain.usecase

import com.example.news.home.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
     operator fun invoke(sources: List<String>) = newsRepository.getNews(sources)

}