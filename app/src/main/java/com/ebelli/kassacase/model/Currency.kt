package com.ebelli.kassacase.model

import com.ebelli.kassacase.R

private const val TRY_PRICE = 27.71F

data class Currency(val name: String?, val balance: Double?) {
    val newBalance: Double?
        get() = balance?.let { TRY_PRICE.div(it) }

    val imageRes: Int
        get() = when (name) {
            "CNY" -> R.drawable.ic_china
            "EUR" -> R.drawable.ic_europe
            "GBP" -> R.drawable.ic_united_kingdom
            "RUB" -> R.drawable.ic_russia
            "USD" -> R.drawable.ic_usa
            else -> R.drawable.ic_europe
        }

    val signRes: Int
        get() = when (name) {
            "CNY" -> R.drawable.ic_yuan
            "EUR" -> R.drawable.ic_euro
            "GBP" -> R.drawable.ic_pound
            "RUB" -> R.drawable.ic_ruble
            "USD" -> R.drawable.ic_dolar
            else -> R.drawable.ic_europe
        }
}