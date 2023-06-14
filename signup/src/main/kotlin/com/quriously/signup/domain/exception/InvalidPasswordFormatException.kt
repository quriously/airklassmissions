package com.quriously.signup.domain.exception

import com.quriously.signup.domain.contract.exception.DomainException

class InvalidPasswordFormatException:DomainException(
    code = "account.signup.invalid_password_format",
    message = "비밀번호는 영문, 숫자, 특수문자 2가지 이상 조합 10자리로 구성되어야 합니다.",
    data = null,
)
