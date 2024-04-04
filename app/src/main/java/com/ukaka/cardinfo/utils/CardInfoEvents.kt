package com.ukaka.cardinfo.utils

sealed class CardChannelEvents {
    object ShowCardDetails: CardChannelEvents()
    data class ShowError(val message: String): CardChannelEvents()
}