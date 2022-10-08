package com.exlab.healthylife.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exlab.healthylife.app.base.ui.BaseViewModel
import com.exlab.healthylife.app.settings.AppSettings
import com.exlab.healthylife.repositories.AccountsRepository
import com.exlab.healthylife.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    accountsRepository: AccountsRepository, logger: Logger
) : BaseViewModel(accountsRepository, logger) {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1500)
            _isLoading.value = false
        }
    }

    fun isSignedIn() = accountsRepository.isSignedIn()
}