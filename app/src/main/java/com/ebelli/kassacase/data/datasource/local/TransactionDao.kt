package com.ebelli.kassacase.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ebelli.kassacase.model.TransactionEntity

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction`")
    suspend fun getAll(): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transactionEntity: TransactionEntity)
}