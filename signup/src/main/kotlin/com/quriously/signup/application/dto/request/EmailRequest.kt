package com.quriously.signup.application.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size


data class EmailRequest(
    @field:Email(message = "이메일 형식이 아닙니다.")
    val email: String,
)

data class CodeRequest(
    @field:Email(message = "이메일 형식이 아닙니다.")
    val email: String,
    @field:Size(min = 6, max = 6, message = "인증번호는 6자리 입니다.")
    val code: String,
)