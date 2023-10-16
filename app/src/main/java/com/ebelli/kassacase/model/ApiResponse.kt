package com.ebelli.kassacase.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("last_updated_at")
    val meta: String?,
    val data: Map<String, Double>?
)