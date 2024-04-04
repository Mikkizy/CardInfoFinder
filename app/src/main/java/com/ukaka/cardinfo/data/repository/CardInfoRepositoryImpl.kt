package com.ukaka.cardinfo.data.repository

import com.ukaka.cardinfo.data.remote.CardInfoApiService
import com.ukaka.cardinfo.domain.models.CardInfoResponse
import com.ukaka.cardinfo.domain.repository.CardInfoRepository
import com.ukaka.cardinfo.network.NetworkConnectivityManager
import com.ukaka.cardinfo.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class CardInfoRepositoryImpl @Inject constructor(
    private val apiService: CardInfoApiService,
    private val networkConnectivityManager: NetworkConnectivityManager
): CardInfoRepository {

    override fun fetchCardInfo(cardNumber: String): Flow<Resource<CardInfoResponse>> = flow {
        if (!networkConnectivityManager.isNetworkAvailable()) {
            emit(Resource.Error("An error occurred! Check Internet Connection"))
        } else {
            emit(Resource.Loading())
            delay(2000L)
            try {
                val response = apiService.fetchCardInfo(cardNumber)
                if (response.isSuccessful) {
                    Timber.d("Response is successful")
                    response.body()?.let { apiResponse ->
                        Timber.d("Card Info is $apiResponse")
                        emit(Resource.Success(apiResponse))
                    } ?: emit(Resource.Error("Sorry, an error occurred!"))
                } else {
                    emit(Resource.Error("Could not fetch card information!"))
                    Timber.d("Response is unsuccessful")
                    Timber.d("Response is ${response.errorBody()?.charStream()?.readText()}")
                }
            }
            catch (e: HttpException) {
                Timber.d("Http Exception Error Http Status Code - ${e.code()} ")
                Timber.d("Http Exception Error Http Status Read Text - ${e.response()?.errorBody()!!.charStream().readText()} ")
                val response = handleHttpException(e)
                emit(Resource.Error(response.toString()))
            }
            catch (e: IOException) {
                Timber.d("IO Exception Error ${e.localizedMessage}")
                emit(Resource.Error(e.localizedMessage))
            } catch (e: Exception) {
                Timber.d("Exception Error ${e.localizedMessage}")
                emit(Resource.Error(e.localizedMessage))
            }
        }
    }

    private fun handleHttpException(e: HttpException): String? {
        return try {
            e.response()?.errorBody()!!.charStream().readText()
        } catch (t: Throwable) {
            Timber.d("Error while handling httpException $t")
            "Oops! An error occurred (${e.code()})"
        }
    }

}