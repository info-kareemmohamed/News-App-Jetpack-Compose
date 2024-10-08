package com.example.news.details.domain.usecase

import com.example.news.core.domain.repository.NewsRepository
import javax.inject.Inject

class UpdateBookmarkStatusUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(url: String, isBookmarked: Boolean) =
        newsRepository.updateBookmarkStatus(url, isBookmarked)

}