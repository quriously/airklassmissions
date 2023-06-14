package com.quriously.signup.domain.port.`in`

import com.quriously.signup.application.security.login.AuthToken
import com.quriously.signup.domain.dto.request.AccountRegisterCommand
import com.quriously.signup.domain.entity.Account

interface AccountRegisterMutatorUseCase {
    fun createAccount(command: AccountRegisterCommand): Account
    fun login(email: String, password: String): Account
}