package com.ebelli.kassacase.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return simpleDateFormat.format(calendar.time)
}