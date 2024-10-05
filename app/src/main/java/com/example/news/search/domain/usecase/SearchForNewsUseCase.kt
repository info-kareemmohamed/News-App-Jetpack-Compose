package com.example.news.search.domain.usecase

import com.example.news.core.domain.repository.NewsRepository
import javax.inject.Inject

class SearchForNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>, searchQuery: String) =
        newsRepository.searchForNews(sources, searchQuery)

}