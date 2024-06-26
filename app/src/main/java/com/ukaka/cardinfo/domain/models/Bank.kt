package com.ukaka.cardinfo.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Bank(
    val city: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val url: String? = null
)