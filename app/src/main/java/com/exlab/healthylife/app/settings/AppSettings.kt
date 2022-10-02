package com.exlab.healthylife.app.settings

interface AppSettings {

    fun getCurrentUserEmail(): String?

    fun setCurrentUserEmail(email: String?)

}