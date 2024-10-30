package com.example.news.payment.domain.repository

import com.example.news.payment.domain.ClientSecretResult
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getClientSecret(amount: Int, currency: String ="EGP"): Flow<ClientSecretResult<String>>
}