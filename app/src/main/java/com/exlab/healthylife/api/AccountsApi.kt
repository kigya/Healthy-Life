package com.exlab.healthylife.api

import com.exlab.healthylife.models.Account
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountsApi {

    @POST("user/login")
    suspend fun signIn(@Body body: Account)

    @POST("user/register")
    suspend fun signUp(@Body body: Account)

}