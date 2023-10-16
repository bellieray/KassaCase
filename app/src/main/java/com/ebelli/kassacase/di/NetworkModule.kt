package com.ebelli.kassacase.di

import android.content.Context
import com.ebelli.kassacase.BuildConfig.BASE_URL
import com.ebelli.kassacase.api.CurrencyService
import com.ebelli.kassacase.data.preferences.Prefs
import com.ebelli.kassacase.data.preferences.PrefsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideService(
        retrofit: Retrofit
    ): CurrencyService {
        return retrofit.create(CurrencyService::class.java)
    }

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext appContext: Context): Prefs = PrefsImpl(appContext)

}