package com.ebelli.kassacase.data.repository

import com.ebelli.kassacase.data.datasource.local.CurrencyLocalDataSource
import com.ebelli.kassacase.data.datasource.remote.CurrencyRemoteDataSource
import com.ebelli.kassacase.model.ApiResponse
import com.ebelli.kassacase.model.TransactionEntity
import com.ebelli.kassacase.utils.NetworkResult
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyRemoteDataSource: CurrencyRemoteDataSource,
    private val currencyLocalDataSource: CurrencyLocalDataSource
) :
    CurrencyRepository {
    override suspend fun getCurrencies(): NetworkResult<ApiResponse> =
        currencyRemoteDataSource.getCurrencies()

    override suspend fun getAllTransactions(): NetworkResult<List<TransactionEntity>> =
        currencyLocalDataSource.getAllTransactions()

    override suspend fun insertTransactionToDb(transactionEntity: TransactionEntity): NetworkResult<Unit> =
        currencyLocalDataSource.insertTransactionToDb(transactionEntity)
}