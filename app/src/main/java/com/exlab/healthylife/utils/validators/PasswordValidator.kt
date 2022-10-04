package com.exlab.healthylife.utils.validators

object PasswordValidator {

    private const val REQUIRED_PASSWORD_LENGTH = 8

    fun isContainsUppercaseLetter(password: String): Boolean {
        return password.any { it.isUpperCase() }
    }

    fun isContainsLowercaseLetter(password: String): Boolean {
        return password.any { it.isLowerCase() }
    }

    fun isContainsDigit(password: String): Boolean {
        return password.any { it.isDigit() }
    }

    fun isValidLength(password: String): Boolean {
        return password.length >= REQUIRED_PASSWORD_LENGTH
    }
}