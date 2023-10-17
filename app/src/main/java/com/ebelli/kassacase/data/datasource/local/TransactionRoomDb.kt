package com.ebelli.kassacase.data.datasource.local

import com.ebelli.kassacase.converter.CurrencyTypeConverter
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ebelli.kassacase.model.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CurrencyTypeConverter::class)
abstract class TransactionRoomDb : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}