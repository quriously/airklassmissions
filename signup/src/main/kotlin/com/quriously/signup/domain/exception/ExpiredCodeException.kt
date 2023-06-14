package com.quriously.signup.domain.exception

import com.quriously.signup.domain.contract.exception.DomainException

class ExpiredCodeException(
) : DomainException(
    code = "account.verify.expired",
    message = "인증 코드의 유효 기간이 지났습니다.",
    data = null,
)