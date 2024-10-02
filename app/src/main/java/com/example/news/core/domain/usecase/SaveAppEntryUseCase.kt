package com.example.news.core.domain.usecase

import com.example.news.core.domain.repository.LocalUserAppEntry
import javax.inject.Inject

class SaveAppEntryUseCase @Inject constructor(
    private val localUserAppEntry: LocalUserAppEntry
){
    suspend operator fun invoke() = localUserAppEntry.saveAppEntry()
}