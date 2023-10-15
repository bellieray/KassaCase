package com.ebelli.kassacase.data.datasource.remote

import com.ebelli.kassacase.api.CurrencyService
import com.ebelli.kassacase.model.ApiResponse
import com.ebelli.kassacase.utils.NetworkResult
import com.ebelli.kassacase.utils.apiCall
import javax.inject.Inject

class CurrencyRemoteDataSourceImpl @Inject constructor(val currencyService: CurrencyService) :
    CurrencyRemoteDataSource {
    override suspend fun getCurrencies(): NetworkResult<ApiResponse> =
        apiCall { currencyService.getCurrencies() }
}