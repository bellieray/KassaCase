package com.ebelli.kassacase.converter

import androidx.room.TypeConverter
import com.ebelli.kassacase.model.Currency
import com.google.gson.Gson

class CurrencyTypeConverter {
    @TypeConverter
    fun currencyToJson(currency: Currency): String {
        return Gson().toJson(currency)
    }

    @TypeConverter
    fun jsonToCurrency(json: String): Currency {
        return Gson().fromJson(json, Currency::class.java)
    }
}