package com.ukaka.cardinfo.presentation

sealed class CardInfoEvents {
    data class OnCardNumberEntered(val number: String): CardInfoEvents()
    data class OnFetchInfoClicked(val cardNumber: String): CardInfoEvents()
}