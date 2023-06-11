package com.quriously.signup.domain.exception

import com.quriously.signup.domain.contract.exception.DomainException

class InvalidDataException(

) : DomainException(
    code = "account.verify.invalid",
    message = "이메일 또는 인증 코드가 올바르지 않습니다.",
    data = null,
)