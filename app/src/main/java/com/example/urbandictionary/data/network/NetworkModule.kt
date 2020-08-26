package com.example.urbandictionary.data.network

import com.example.urbandictionary.UrbanDictionary
import com.example.urbandictionary.viewmodel.SearchViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://mashape-community-urban-dictionary.p.rapidapi.com/"

@Module
open class NetworkModule {
    @Provides
    @Singleton
    open fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    @Provides
    @Singleton
    open fun provideInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    open fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    open fun provideRetrofit(okHttpClient: OkHttpClient, url: String, gsonConverterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder().baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()

    @Provides
    @Singleton
    open fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    open fun provideUrl(): String =
        BASE_URL
}

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {

    fun inject(viewModel: SearchViewModel)
    fun inject(urbanDictionary: UrbanDictionary)

    companion object {
        var instance: NetworkComponent? = null

        fun initNetworkComponent(module: NetworkModule) {
            instance = DaggerNetworkComponent.builder().networkModule(module).build()
        }
    }
}