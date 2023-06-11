package com.quriously.signup.domain.exception

import com.quriously.signup.domain.contract.exception.DomainException

class InvalidAccountAndAccountVerifyException:DomainException(
    code = "account.invalid",
    data = null,
    message = "가입하려는 email과 인증된 email이 다릅니다.",
) {
}