package com.hassanmohammed.movieapp.di

import com.hassanmohammed.movieapp.data.ApiService
import com.hassanmohammed.movieapp.utils.API_KEY
import com.hassanmohammed.movieapp.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 *Created by Hassan Mohammed on 6/28/21
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideApiKeyQueryInterceptor(): Interceptor {
        return Interceptor { chain ->
            var original = chain.request()
            val url = original.url.newBuilder().addQueryParameter("api_key", API_KEY).build()
            original = original.newBuilder().url(url).build()
            chain.proceed(original)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(apiKeyQueryInterceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(apiKeyQueryInterceptor)
            .addInterceptor(loggingInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}