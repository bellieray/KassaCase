package com.ebelli.kassacase.model

data class Transaction(
    val currency: Currency,
    val purchaseDate: String,
    val purchaseTime: String,
    val purchasedQty: String
)