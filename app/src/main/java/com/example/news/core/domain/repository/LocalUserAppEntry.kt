package com.example.news.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface LocalUserAppEntry {
    suspend fun saveAppEntry()
    fun readAppEntry(): Flow<Boolean>
}