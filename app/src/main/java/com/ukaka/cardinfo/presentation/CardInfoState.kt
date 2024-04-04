package com.ukaka.cardinfo.presentation

import android.text.BoringLayout

data class CardInfoState(
    val cardNumber: String = "",
    val cardBrand: String = "",
    val cardType: String = "",
    val country: String = "",
    val bank: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false
)
