package com.quriously.signup.application.dto.request

import jakarta.validation.constraints.Email


data class EmailRequest(
    @field:Email(message = "이메일 형식이 아닙니다.")
    val email: String,
)

data class CodeRequest(
    @field:Email(message = "이메일 형식이 아닙니다.")
    val email: String,
    val code: String,
)