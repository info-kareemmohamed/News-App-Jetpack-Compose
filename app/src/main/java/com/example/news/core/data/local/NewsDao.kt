package com.example.news.core.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.news.core.domain.model.Article
import com.example.news.core.domain.model.SourceType
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Upsert
    suspend fun upsert(articles: List<Article>)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM Article WHERE isBookMarked = 1")
    fun getBookMarkedArticles(): Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE isBookMarked = 1 and sourceType = :sourceType")
    fun getBookMarkedArticlesForBookmark(sourceType: SourceType): Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE sourceType = :sourceType")
    fun getArticles(sourceType: SourceType): PagingSource<Int, Article>

    @Query("UPDATE Article SET isBookMarked = :isBookMarked WHERE url = :url and sourceType = :sourceType")
    suspend fun updateBookmarkStatus(url: String,isBookMarked: Boolean,sourceType: SourceType)

    @Query("SELECT * FROM Article WHERE isBookMarked = 0 ")
    suspend fun getNotBookMarkedArticles(): List<Article>

    @Query("DELETE FROM Article WHERE sourceType = :sourceType")
    suspend fun clearArticles(sourceType: SourceType)

    @Query("SELECT * FROM Article WHERE url=:url")
    suspend fun getArticle(url: String): Article?

}