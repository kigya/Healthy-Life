package com.exlab.healthylife.ui.screens.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.exlab.healthylife.R
import com.exlab.healthylife.app.base.ui.BaseViewModel
import com.exlab.healthylife.models.UserField
import com.exlab.healthylife.repositories.AccountsRepository
import com.exlab.healthylife.utils.*
import com.exlab.healthylife.utils.extensions.requireValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    accountsRepository: AccountsRepository,
    logger: Logger
) : BaseViewModel(accountsRepository, logger) {

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _clearPasswordEvent = MutableUnitLiveEvent()
    val clearPasswordEvent = _clearPasswordEvent.share()

    private val _showAuthErrorToastEvent = MutableLiveEvent<Int>()
    val showAuthToastEvent = _showAuthErrorToastEvent.share()

    private val _navigateToTabsEvent = MutableUnitLiveEvent()
    val navigateToTabsEvent = _navigateToTabsEvent.share()

    private val _goBackEvent = MutableUnitLiveEvent()
    val goBackEvent = _goBackEvent.share()

    fun signIn(email: String, password: String) = viewModelScope.safeLaunch {
        showProgress()
        try {
            accountsRepository.signIn(email, password)
            launchTabsScreen()
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        } catch (e: InvalidCredentialsException) {
            processInvalidCredentialsException()
        } catch (e: AccountAlreadyExistsException) {
            _showAuthErrorToastEvent.publishEvent(R.string.invalid_email_or_password)
        } catch (e: ParseBackendResponseException) {
            _showAuthErrorToastEvent.publishEvent(R.string.auth_error)
        } finally {
            hideProgress()
        }
    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = _state.requireValue().copy(
            emptyEmailError = e.field == UserField.Email,
            emptyPasswordError = e.field == UserField.Password
        )
    }

    private fun processInvalidCredentialsException() {
        clearPasswordField()
        showAuthErrorToast()
    }

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(signInInProgress = false)
    }

    private fun clearPasswordField() = _clearPasswordEvent.publishEvent()

    private fun showAuthErrorToast() =
        _showAuthErrorToastEvent.publishEvent(R.string.invalid_email_or_password)

    private fun launchTabsScreen() = _navigateToTabsEvent.publishEvent()

    data class State(
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
        val enableViews: Boolean get() = !signInInProgress
    }
}