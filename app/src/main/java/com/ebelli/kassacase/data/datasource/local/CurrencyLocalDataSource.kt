package com.ebelli.kassacase.data.datasource.local

import com.ebelli.kassacase.model.TransactionEntity
import com.ebelli.kassacase.utils.NetworkResult

interface CurrencyLocalDataSource {
    suspend fun getAllTransactions(): NetworkResult<List<TransactionEntity>>
    suspend fun insertTransactionToDb(transactionEntity: TransactionEntity): NetworkResult<Unit>
}