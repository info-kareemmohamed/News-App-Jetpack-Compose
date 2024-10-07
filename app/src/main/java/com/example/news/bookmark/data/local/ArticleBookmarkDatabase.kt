package com.example.news.bookmark.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news.core.domain.model.Article

@Database(entities = [Article::class], version = 1)
abstract class ArticleBookmarkDatabase : RoomDatabase() {
    abstract val dao: ArticleBookmarkDao
}