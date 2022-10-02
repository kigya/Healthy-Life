package com.exlab.healthylife.models

import com.exlab.healthylife.utils.EmptyFieldException

data class Account(
    val email: String,
    val password: String
) {
    fun validate() {
        if (email.isBlank()) throw EmptyFieldException(UserField.Email)
        if (password.isBlank()) throw EmptyFieldException(UserField.Password)
    }
}

