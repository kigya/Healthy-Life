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

    override suspend fun signIn(
        email: String,
        password: String
    ) = wrapRetrofitExceptions {
        val signInRequestEntity = Account(email, password)
        accountsApi.signIn(signInRequestEntity)
    }

    override suspend fun signUp(
        account: Account
    ) = wrapRetrofitExceptions {
        accountsApi.signUp(account)
    }

    override suspend fun getAccount(): Account {
        return Account("1", "test")
    }
}