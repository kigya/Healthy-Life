package com.exlab.healthylife.app.settings

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [AppSettings] based on [SharedPreferences].
 */
@Singleton
class SharedPreferencesAppSettings @Inject constructor(
    @ApplicationContext appContext: Context
) : AppSettings {

    private val sharedPreferences =
        appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override fun setCurrentUserToken(email: String?) {
        val editor = sharedPreferences.edit()
        if (email.isNullOrEmpty()) editor.remove(PREF_CURRENT_ACCOUNT_EMAIL)
        else editor.putString(PREF_CURRENT_ACCOUNT_EMAIL, email)
        editor.apply()
    }

    override fun getCurrentUserToken(): String? =
        sharedPreferences.getString(PREF_CURRENT_ACCOUNT_EMAIL, null)

    companion object {
        private const val PREF_CURRENT_ACCOUNT_EMAIL = "currentToken"
    }
}