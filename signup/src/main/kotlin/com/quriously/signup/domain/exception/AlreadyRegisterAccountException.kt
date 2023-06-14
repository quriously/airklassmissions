package com.quriously.signup.domain.exception

import com.quriously.signup.domain.contract.exception.DomainException

class AlreadyRegisterAccountException(
    email: String,
) : DomainException(
    code = "account.register.already",
    message = "이미 가입된 계정입니다.",
    data = mapOf(
        "email" to email
    )
)