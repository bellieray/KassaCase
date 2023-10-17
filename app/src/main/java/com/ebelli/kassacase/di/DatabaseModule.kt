package com.ebelli.kassacase.di

import android.app.Application
import androidx.room.Room
import com.ebelli.kassacase.data.datasource.local.TransactionDao
import com.ebelli.kassacase.data.datasource.local.TransactionRoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideTransactionDB(app: Application): TransactionRoomDb {
        return Room.databaseBuilder(
            app, TransactionRoomDb::class.java, "transaction_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(cryptoDB: TransactionRoomDb): TransactionDao {
        return cryptoDB.transactionDao()
    }
}