package com.example.news.bookmark.domain.usecase

import com.example.news.bookmark.domain.repository.ArticleBookmarkRepository
import com.example.news.core.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articleBookMarkRepository: ArticleBookmarkRepository
) {
    operator fun invoke(): Flow<List<Article>> {
        return articleBookMarkRepository.getArticles()
    }

}