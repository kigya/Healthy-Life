package com.exlab.healthylife.models

import com.squareup.moshi.Json


data class Account(
    @Json(name="email")
    val email: String,
    @Json(name="password")
    val password: String
)

