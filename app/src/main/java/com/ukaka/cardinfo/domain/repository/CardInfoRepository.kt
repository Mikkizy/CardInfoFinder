package com.ukaka.cardinfo.domain.repository

import com.ukaka.cardinfo.domain.models.CardInfoResponse
import com.ukaka.cardinfo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CardInfoRepository {

    fun fetchCardInfo(cardNumber: String): Flow<Resource<CardInfoResponse>>

}