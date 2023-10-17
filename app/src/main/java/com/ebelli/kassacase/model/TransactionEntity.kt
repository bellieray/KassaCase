package com.ebelli.kassacase.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val currency: Currency,
    val purchaseDate: String,
    val purchasedQty: Double
)