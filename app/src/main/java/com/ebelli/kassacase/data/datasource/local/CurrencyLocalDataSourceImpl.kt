package com.ebelli.kassacase.data.datasource.local

import com.ebelli.kassacase.model.TransactionEntity
import com.ebelli.kassacase.utils.NetworkResult
import javax.inject.Inject

class CurrencyLocalDataSourceImpl @Inject constructor(private val transactionDao: TransactionDao) :
    CurrencyLocalDataSource {
    override suspend fun getAllTransactions(): NetworkResult<List<TransactionEntity>> {
        return try {
            val response = transactionDao.getAll()
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Failed(e.localizedMessage)
        }
    }

    override suspend fun insertTransactionToDb(transactionEntity: TransactionEntity): NetworkResult<Unit> {
        return try {
            NetworkResult.Success(transactionDao.insert(transactionEntity))
        } catch (e: Exception) {
            NetworkResult.Failed(e.localizedMessage)
        }
    }
}