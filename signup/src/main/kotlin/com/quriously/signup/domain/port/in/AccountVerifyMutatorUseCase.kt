package com.quriously.signup.domain.port.`in`

interface AccountVerifyMutatorUseCase {
    fun sendCode(email: String): String
    fun verifyCode(email: String, code: String): Long
}