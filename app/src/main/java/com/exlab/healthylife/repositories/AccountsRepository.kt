package com.exlab.healthylife.repositories

import com.exlab.healthylife.app.base.Result
import com.exlab.healthylife.app.settings.AppSettings
import com.exlab.healthylife.models.Account
import com.exlab.healthylife.models.UserField
import com.exlab.healthylife.utils.*
import com.exlab.healthylife.utils.async.LazyFlowSubject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountsRepository @Inject constructor(
    private val accountsSource: AccountsSource,
    private val appSettings: AppSettings
) {

    private val accountLazyFlowSubject = LazyFlowSubject<Unit, Account> {
        doGetAccount()
    }

    fun isSignedIn(): Boolean {
        return appSettings.getCurrentUserToken() != null
    }

    suspend fun signIn(email: String, password: String) {
        if (email.isBlank()) throw EmptyFieldException(UserField.Email)
        if (password.isBlank()) throw EmptyFieldException(UserField.Password)

        try {
            accountsSource.signIn(email, password)
        } catch (e: Exception) {
            if (e is BackendException && e.code == 400) {
                throw InvalidCredentialsException(e)
            } else {
                throw e
            }
        }
        appSettings.setCurrentUserToken(email)
        accountLazyFlowSubject.updateAllValues(accountsSource.getAccount())
    }

    suspend fun signUp(account:Account) {
        try {
            accountsSource.signUp(account)
        } catch (e: BackendException) {
            if (e.code == 400) throw AccountAlreadyExistsException(e)
            else throw e
        }
    }

    fun reloadAccount() {
        accountLazyFlowSubject.reloadAll()
    }

    fun getAccount(): Flow<Result<Account>> {
        return accountLazyFlowSubject.listen(Unit)
    }

    private suspend fun doGetAccount(): Account = wrapBackendExceptions {
        try {
            accountsSource.getAccount()
        } catch (e: BackendException) {
            // account has been deleted = session expired = AuthException
            if (e.code == 400) throw AuthException(e)
            else throw e
        }
    }

    fun logout() {
        appSettings.setCurrentUserToken(null)
        accountLazyFlowSubject.updateAllValues(null)
    }

}