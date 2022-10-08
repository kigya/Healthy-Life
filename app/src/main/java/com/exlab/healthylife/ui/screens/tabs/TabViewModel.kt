package com.exlab.healthylife.ui.screens.tabs

import com.exlab.healthylife.app.base.ui.BaseViewModel
import com.exlab.healthylife.app.settings.AppSettings
import com.exlab.healthylife.repositories.AccountsRepository
import com.exlab.healthylife.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TabViewModel @Inject constructor(
    accountsRepository: AccountsRepository, logger: Logger,
    val appSettings: AppSettings
) : BaseViewModel(accountsRepository, logger) {

    fun getCurrentUser() = appSettings.getCurrentUserToken()
}
