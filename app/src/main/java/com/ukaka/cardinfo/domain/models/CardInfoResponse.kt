package com.ukaka.cardinfo.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CardInfoResponse(
    val bank: Bank? = null,
    val brand: String? = null,
    val country: Country? = null,
    val number: Number? = null,
    val prepaid: Boolean? = null,
    val scheme: String? = null,
    val type: String? = null
)