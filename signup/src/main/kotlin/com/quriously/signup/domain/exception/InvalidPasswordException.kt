package com.quriously.signup.domain.exception

import com.quriously.signup.domain.contract.exception.DomainException

class InvalidPasswordException : DomainException(
    code = "account.password.invalid",
    message = "입력하신 비밀번호가 다릅니다.",
    data = null,
)