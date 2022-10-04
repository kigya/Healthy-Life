package com.exlab.healthylife.api

import kotlinx.coroutines.delay
import com.exlab.healthylife.app.base.network.BaseRetrofitSource
import com.exlab.healthylife.app.base.network.RetrofitConfig
import com.exlab.healthylife.models.Account
import com.exlab.healthylife.repositories.AccountsSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitAccountsSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), AccountsSource {

    private val accountsApi = retrofit.create(AccountsApi::class.java)

    override suspend fun signUp(
        account: Account
    ) = wrapRetrofitExceptions {
        delay(1000)
        accountsApi.signUp(account)
    }

    override suspend fun getAccount(): Account {
        return Account("1", "test")
    }
}