package com.example.news.details.domain.usecase

import com.example.news.core.domain.model.Article
import com.example.news.core.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article) = newsRepository.delete(article)

}