package com.ebelli.kassacase.di

import com.ebelli.kassacase.api.CurrencyService
import com.ebelli.kassacase.data.datasource.local.CurrencyLocalDataSource
import com.ebelli.kassacase.data.datasource.local.CurrencyLocalDataSourceImpl
import com.ebelli.kassacase.data.datasource.local.TransactionDao
import com.ebelli.kassacase.data.datasource.remote.CurrencyRemoteDataSource
import com.ebelli.kassacase.data.datasource.remote.CurrencyRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideCurrencyRemoteDataSource(
        currencyService: CurrencyService,
    ): CurrencyRemoteDataSource =
        CurrencyRemoteDataSourceImpl(currencyService)

    @Singleton
    @Provides
    fun provideCurrencyLocalDataSource(
        transactionDao: TransactionDao,
    ): CurrencyLocalDataSource =
        CurrencyLocalDataSourceImpl(transactionDao)
}