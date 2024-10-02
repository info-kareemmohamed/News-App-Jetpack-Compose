package com.example.news.core.di

import com.example.news.onboarding.data.PageData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providePageData(): PageData {
        return PageData
    }

}