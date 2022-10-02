package com.exlab.healthylife.repositories

import com.exlab.healthylife.models.Account

interface AccountsSource {

    suspend fun signUp(account: Account)

    suspend fun getAccount(): Account

}