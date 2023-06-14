package com.quriously.signup.domain.dto.request

import com.quriously.signup.domain.entity.AccountTermType
import java.util.*

data class AccountRegisterCommand(
    val email: String,
    val accountVerifyId: Long,
    val password: String,
    val passwordConfirm: String,
    val terms: List<AccountTermType>,
    val birthday: Date,
)