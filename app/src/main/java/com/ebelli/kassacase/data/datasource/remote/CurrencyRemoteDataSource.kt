package com.ebelli.kassacase.data.datasource.remote

import com.ebelli.kassacase.model.ApiResponse
import com.ebelli.kassacase.utils.NetworkResult

interface CurrencyRemoteDataSource {
    suspend fun getCurrencies(): NetworkResult<ApiResponse>
}