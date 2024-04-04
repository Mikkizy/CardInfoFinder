package com.ukaka.cardinfo.dependency_injection

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ukaka.cardinfo.data.remote.CardInfoApiService
import com.ukaka.cardinfo.data.repository.CardInfoRepositoryImpl
import com.ukaka.cardinfo.domain.repository.CardInfoRepository
import com.ukaka.cardinfo.network.NetworkConnectivityManager
import com.ukaka.cardinfo.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@OptIn(ExperimentalSerializationApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) = context

    @Provides
    @Singleton
    fun provideApiService(): CardInfoApiService {
        // logging interceptor to log the body of every request and response, useful for debugging
        val loggerInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        // http client
        val okHttpClient by lazy {
            OkHttpClient.Builder().apply {
                this
                    .addInterceptor(loggerInterceptor)
                    .addInterceptor(
                        Interceptor { chain: Interceptor.Chain ->
                            val request = chain
                                .request()
                                .newBuilder()
                                .addHeader("Connection", "close")
                                .build()
                            chain.proceed(request)
                        }
                    )
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
            }.build()
        }

        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(CardInfoApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesNetworkManager(
        @ApplicationContext appContext: Context
    ): NetworkConnectivityManager {
        return NetworkConnectivityManager(appContext)
    }

    @Provides
    @Singleton
    fun provideCardInfoRepository(apiService: CardInfoApiService, networkConnectivityManager: NetworkConnectivityManager): CardInfoRepository {
        return CardInfoRepositoryImpl(apiService, networkConnectivityManager)
    }
}