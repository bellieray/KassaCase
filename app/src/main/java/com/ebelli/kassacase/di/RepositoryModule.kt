package com.ebelli.kassacase.di

import com.ebelli.kassacase.data.datasource.remote.CurrencyRemoteDataSource
import com.ebelli.kassacase.data.repository.CurrencyRepository
import com.ebelli.kassacase.data.repository.CurrencyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideCurrencyRepository(
        currencyRemoteDataSource: CurrencyRemoteDataSource
    ): CurrencyRepository =
        CurrencyRepositoryImpl(currencyRemoteDataSource)
}