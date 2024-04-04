package com.ukaka.cardinfo.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Number(
    val length: Int? = null,
    val luhn: Boolean? = null
)