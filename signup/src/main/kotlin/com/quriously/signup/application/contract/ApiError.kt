package com.quriously.signup.application.contract

data class ApiError (
    val code: String?,
    val message: String?,
    val data: Map<String, Any?>? = null
)