package com.example.news.payment.data.remote

import PaymentRequest
import PaymentResponse
import com.example.news.payment.data.Constants.SECRET_KEY
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PaymentService {
    @Headers("Authorization: Token $SECRET_KEY")
    @POST("v1/intention/")
    suspend fun getClientSecret(@Body paymentRequest: PaymentRequest): PaymentResponse

}