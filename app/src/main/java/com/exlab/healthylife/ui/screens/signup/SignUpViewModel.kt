package com.exlab.healthylife.ui.screens.signup

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.exlab.healthylife.R
import com.exlab.healthylife.app.base.ui.BaseViewModel
import com.exlab.healthylife.models.Account
import com.exlab.healthylife.models.UserField
import com.exlab.healthylife.repositories.AccountsRepository
import com.exlab.healthylife.utils.*
import com.exlab.healthylife.utils.extensions.requireValue
import com.exlab.healthylife.utils.mediator.PairMediatorLiveData
import com.exlab.healthylife.utils.mediator.TripleMediatorLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    accountsRepository: AccountsRepository, logger: Logger
) : BaseViewModel(accountsRepository, logger) {

    private val _goBackEvent = MutableUnitLiveEvent()
    val goBackEvent = _goBackEvent.share()

    private val _showToastEvent = MutableLiveEvent<Int>()
    val showToastEvent = _showToastEvent.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _isPasswordValid = MutableLiveData(false)

    private val _isEmailValid = MutableLiveData(false)

    private val _isAgreePolicyActivated = MutableLiveData(false)

    private val _isAgreeDataProcessingActivated = MutableLiveData(false)

    private val inputFieldsMediator = PairMediatorLiveData(_isPasswordValid, _isEmailValid)

    val fieldsTripleValid = TripleMediatorLiveData(
        inputFieldsMediator, _isAgreePolicyActivated, _isAgreeDataProcessingActivated
    )

    fun setAgreePolicyActivated(isActivated: Boolean) {
        _isAgreePolicyActivated.value = isActivated
    }

    fun setAgreeDataProcessingActivated(isActivated: Boolean) {
        _isAgreeDataProcessingActivated.value = isActivated
    }

    fun setPasswordValid(isValid: Boolean) {
        _isPasswordValid.value = isValid
    }

    fun setEmailValid(isValid: Boolean) {
        _isEmailValid.value = isValid
    }

    fun signUp(account: Account) = viewModelScope.safeLaunch {
        showProgress()
        try {
            accountsRepository.signUp(account)
            showSuccessSignUpMessage()
            goBack()
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        } catch (e: AccountAlreadyExistsException) {
            processAccountAlreadyExistsException()
        } finally {
            hideProgress()
        }
    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = when (e.field) {
            UserField.Email -> _state.requireValue()
                .copy(emailErrorMessageRes = R.string.field_is_empty)
            UserField.Password -> _state.requireValue()
                .copy(passwordErrorMessageRes = R.string.field_is_empty)
        }
    }

    private fun processAccountAlreadyExistsException() {
        _state.value =
            _state.requireValue().copy(emailErrorMessageRes = R.string.account_already_exists)
    }

    private fun showProgress() {
        _state.value = State(signUpInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(signUpInProgress = false)
    }

    private fun showSuccessSignUpMessage() = _showToastEvent.publishEvent(R.string.sign_up_success)

    private fun goBack() = _goBackEvent.publishEvent()

    data class State(
        @StringRes val emailErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val passwordErrorMessageRes: Int = NO_ERROR_MESSAGE,
        val signUpInProgress: Boolean = false,
    ) {
        val showProgress: Boolean get() = signUpInProgress
        val enableViews: Boolean get() = !signUpInProgress
    }

    companion object {
        const val NO_ERROR_MESSAGE = 0
    }

}