package com.example.news.details.domain.usecase

import com.example.news.core.domain.model.SourceType
import com.example.news.core.domain.repository.NewsRepository
import javax.inject.Inject

class UpdateBookmarkStatusUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(
        url: String,
        isBookmarked: Boolean,
        sourceType: SourceType = SourceType.BOOKMARK
    ) =
        newsRepository.updateBookmarkStatus(url, isBookmarked, sourceType)

}
