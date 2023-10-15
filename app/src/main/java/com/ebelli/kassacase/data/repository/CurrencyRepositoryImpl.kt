package com.ebelli.kassacase.data.repository

import com.ebelli.kassacase.data.datasource.remote.CurrencyRemoteDataSource
import com.ebelli.kassacase.model.ApiResponse
import com.ebelli.kassacase.utils.NetworkResult
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val currencyRemoteDataSource: CurrencyRemoteDataSource) :
    CurrencyRepository {
    override suspend fun getCurrencies(): NetworkResult<ApiResponse> =
        currencyRemoteDataSource.getCurrencies()

}