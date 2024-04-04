package com.ukaka.cardinfo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ukaka.cardinfo.domain.repository.CardInfoRepository
import com.ukaka.cardinfo.utils.CardChannelEvents
import com.ukaka.cardinfo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardInfoViewModel @Inject constructor(
    private val repository: CardInfoRepository
): ViewModel(){

    private val _cardInfoState = MutableStateFlow(CardInfoState())
    val cardInfoState = _cardInfoState.asStateFlow()

    private val eventChannel = MutableSharedFlow<CardChannelEvents>()
    val events = eventChannel.asSharedFlow()

    fun fetchCardInfo(cardNumber: String) {
        viewModelScope.launch {
            repository.fetchCardInfo(cardNumber).onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _cardInfoState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        val result = resource.data
                        _cardInfoState.update {
                            it.copy(
                                cardBrand = result?.brand.toString(),
                                cardType = result?.type.toString(),
                                country = result?.country?.name.toString() + " " + result?.country?.emoji,
                                bank = result?.bank?.name.toString(),
                                isLoading = false,
                                isError = false,
                                isSuccess = true,
                                errorMessage = ""
                            )
                        }
                        eventChannel.emit(CardChannelEvents.ShowCardDetails)
                    }
                    is Resource.Error -> {
                        val errorMessage = resource.message
                        _cardInfoState.update {
                            it.copy(isLoading = false, isSuccess = false, isError = true, errorMessage = errorMessage.toString())
                        }
                        eventChannel.emit(CardChannelEvents.ShowError(errorMessage.toString()))
                    }

                    else -> {}
                }
            }.launchIn(this)
        }
    }

    fun onEvent(event: CardInfoEvents) {
        when (event) {
            is CardInfoEvents.OnCardNumberEntered -> {
                _cardInfoState.update {
                    it.copy(cardNumber = event.number)
                }
            }
            is CardInfoEvents.OnFetchInfoClicked -> {
                fetchCardInfo(event.cardNumber)
            }
        }
    }
}