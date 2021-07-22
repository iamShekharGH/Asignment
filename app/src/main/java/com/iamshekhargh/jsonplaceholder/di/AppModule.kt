package com.iamshekhargh.jsonplaceholder.di

import com.iamshekhargh.jsonplaceholder.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by <<-- iamShekharGH -->>
 * on 22 July 2021, Thursday
 * at 1:25 AM
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    // Retrofit

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiInterface.BASE_URL).build()

    @Provides
    @Singleton
    fun provideJsonPlaceHolderApi(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)
}