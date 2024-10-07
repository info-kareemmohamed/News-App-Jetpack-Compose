package com.example.news.core.di

import android.app.Application
import androidx.room.Room
import com.example.news.bookmark.Constants.BOOKMARK_NAME_DB
import com.example.news.core.data.local.NewsDao
import com.example.news.core.data.local.NewsDatabase
import com.example.news.core.data.repository.LocalUserAppEntryImpl
import com.example.news.core.domain.repository.LocalUserAppEntry
import com.example.news.core.util.Constants.BASE_URL
import com.example.news.core.data.remote.NewsApi
import com.example.news.core.data.repository.NewsRepositoryImpl
import com.example.news.core.domain.repository.NewsRepository
import com.example.news.onboarding.data.PageData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePageData(): PageData {
        return PageData
    }

    @Provides
    @Singleton
    fun provideLocalUserAppEntry(application: Application): LocalUserAppEntry {
        return LocalUserAppEntryImpl(application)
    }

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }


    @Provides
    @Singleton
    fun provideArticleBookMarkDatabase(application: Application): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = BOOKMARK_NAME_DB,
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticleBookMarkDao(newsDatabase: NewsDatabase) = newsDatabase.dao


    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi, newsDao: NewsDao): NewsRepository =
        NewsRepositoryImpl(newsApi, newsDao)


}