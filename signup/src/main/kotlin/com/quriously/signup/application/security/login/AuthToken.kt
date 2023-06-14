package com.quriously.signup.application.security.login

data class AuthToken(
    val access: String,
    val refresh: String,
)