package com.example.news.core.domain.usecase

import com.example.news.core.domain.repository.LocalUserAppEntry
import javax.inject.Inject

class ReadAppEntryUseCase @Inject constructor(
    private val localUserAppEntry: LocalUserAppEntry
) {
    operator fun invoke() = localUserAppEntry.readAppEntry()
}