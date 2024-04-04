package com.ukaka.cardinfo.presentation

import com.ukaka.cardinfo.MainDispatcherRule
import com.ukaka.cardinfo.domain.models.CardInfoResponse
import com.ukaka.cardinfo.domain.repository.CardInfoRepository
import com.ukaka.cardinfo.utils.CardChannelEvents
import com.ukaka.cardinfo.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CardInfoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var repository: CardInfoRepository

    private lateinit var viewModel: CardInfoViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = CardInfoViewModel(repository)
    }

    @Test
    fun `fetchCardInfo should update state when resource is success`() = runTest {
        val cardNumber = ""
        val cardInfoResponse = CardInfoResponse()
        val successResource = Resource.Success(cardInfoResponse)
        `when`(repository.fetchCardInfo(cardNumber)).thenReturn(flowOf(successResource))

        viewModel.fetchCardInfo(cardNumber)

        assertEquals(viewModel.cardInfoState.value, CardInfoState(
            cardNumber = cardNumber,
            cardBrand = cardInfoResponse.brand.toString(),
            cardType = cardInfoResponse.type.toString(),
            country = "${cardInfoResponse.country?.name} ${cardInfoResponse.country?.emoji}",
            bank = cardInfoResponse.bank?.name.toString(),
            isLoading = false,
            isError = false,
            isSuccess = true,
            errorMessage = ""
        ))
    }
}