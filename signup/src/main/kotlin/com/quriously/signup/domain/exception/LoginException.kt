package com.quriously.signup.domain.exception

import com.quriously.signup.domain.contract.exception.DomainException

class LoginException : DomainException(
    code = "account.login.fail",
    message = "이메일 또는 비밀번호가 올바르지 않습니다.",
    data = null
)