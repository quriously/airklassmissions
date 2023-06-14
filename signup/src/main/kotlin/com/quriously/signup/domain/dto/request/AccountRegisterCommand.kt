package com.quriously.signup.domain.dto.request

import com.quriously.signup.domain.entity.AccountTermType
import java.time.LocalDate
import java.util.*

data class AccountRegisterCommand(
    val email: String,
    val name: String,
    val accountVerifyId: Long,
    val password: String,
    val passwordConfirm: String,
    val terms: List<AccountTermType>,
    val birthday: LocalDate,
)