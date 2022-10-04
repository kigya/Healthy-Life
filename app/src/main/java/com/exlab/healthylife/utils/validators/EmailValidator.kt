package com.exlab.healthylife.utils.validators

import com.exlab.healthylife.utils.Constants

object EmailValidator {
    fun isValidEmail(text: CharSequence?) = text?.matches(Regex(Constants.EMAIL_REGEX)) ?: false
}