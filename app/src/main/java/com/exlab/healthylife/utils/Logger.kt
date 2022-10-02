package com.exlab.healthylife.utils

interface Logger {

    fun log(tag: String, message: String)

    fun error(tag: String, e: Throwable)

}