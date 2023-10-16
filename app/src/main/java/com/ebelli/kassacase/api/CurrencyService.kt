package com.ebelli.kassacase.api

import com.ebelli.kassacase.BuildConfig.API_KEY
import com.ebelli.kassacase.model.ApiResponse
import com.ebelli.kassacase.utils.SELECTED_CURRENCIES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
    @GET(".")
    suspend fun getCurrencies(
        @Query("apikey") apikey: String = API_KEY,
        @Query("currencies") currencies: String = SELECTED_CURRENCIES
    ): Response<ApiResponse>
}