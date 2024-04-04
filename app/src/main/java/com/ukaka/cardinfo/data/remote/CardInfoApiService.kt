package com.ukaka.cardinfo.data.remote

import com.ukaka.cardinfo.domain.models.CardInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CardInfoApiService {
    @GET("{card_number}")
    suspend fun fetchCardInfo(
        @Path("card_number") cardNumber: String
    ): Response<CardInfoResponse>
}