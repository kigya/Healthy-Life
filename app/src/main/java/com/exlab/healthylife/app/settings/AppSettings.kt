package com.exlab.healthylife.app.settings

interface AppSettings {

    fun getCurrentUserToken(): String?

    fun setCurrentUserToken(email: String?)

}