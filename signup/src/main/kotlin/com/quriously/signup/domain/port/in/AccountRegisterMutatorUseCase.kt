package com.quriously.signup.domain.port.`in`

import com.quriously.signup.domain.dto.request.AccountRegisterCommand
import com.quriously.signup.domain.entity.Account

interface AccountRegisterMutatorUseCase {
    fun createAccount(command: AccountRegisterCommand): Account
}