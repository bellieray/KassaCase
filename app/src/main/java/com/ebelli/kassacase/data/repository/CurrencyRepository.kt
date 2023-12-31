package com.ebelli.kassacase.data.repository

import com.ebelli.kassacase.model.ApiResponse
import com.ebelli.kassacase.model.TransactionEntity
import com.ebelli.kassacase.utils.NetworkResult

interface CurrencyRepository {
    suspend fun getCurrencies() : NetworkResult<ApiResponse>
    suspend fun getAllTransactions(): NetworkResult<List<TransactionEntity>>
    suspend fun insertTransactionToDb(transactionEntity: TransactionEntity): NetworkResult<Unit>
}