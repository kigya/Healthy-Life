package com.exlab.healthylife.repositories

import com.exlab.healthylife.app.base.Result
import com.exlab.healthylife.app.settings.AppSettings
import com.exlab.healthylife.models.Account
import com.exlab.healthylife.utils.AccountAlreadyExistsException
import com.exlab.healthylife.utils.AuthException
import com.exlab.healthylife.utils.BackendException
import com.exlab.healthylife.utils.async.LazyFlowSubject
import com.exlab.healthylife.utils.wrapBackendExceptions
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

    /**
     * Whether user is signed-in or not.
     */
    fun isSignedIn(): Boolean {
        return appSettings.getCurrentUserEmail() != null
    }

    suspend fun signUp(account:Account) {
        try {
            accountsSource.signUp(account)
        } catch (e: BackendException) {
            if (e.code == 400) throw AccountAlreadyExistsException(e)
            else throw e
        }
    }


    /**
     * Reload account info. Results of reloading are delivered to the flows
     * returned by [getAccount] method.
     */
    fun reloadAccount() {
        accountLazyFlowSubject.reloadAll()
    }

    /**
     * Get the account info of the current signed-in user and listen for all
     * further changes of the account data.
     * If user is not logged-in an empty result is emitted.
     * @return infinite flow, always success; errors are wrapped to [Result]
     */
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
        appSettings.setCurrentUserEmail(null)
        accountLazyFlowSubject.updateAllValues(null)
    }

}