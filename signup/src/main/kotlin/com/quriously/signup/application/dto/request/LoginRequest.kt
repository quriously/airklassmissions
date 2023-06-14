package com.quriously.signup.application.dto.request

data class LoginRequest(
    val email: String,
    val password: String,
)