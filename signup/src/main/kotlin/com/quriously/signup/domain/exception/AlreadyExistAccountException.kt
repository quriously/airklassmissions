package com.quriously.signup.domain.exception

import com.quriously.signup.domain.contract.exception.DomainException

class AlreadyExistAccountException : DomainException(
    code = "account.already.exist",
    message = "이미 가입된 email입니다.",
    data = null,
)