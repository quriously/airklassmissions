package com.quriously.signup.domain.exception

import com.quriously.signup.domain.contract.exception.DomainException

class NotFoundEmailException:DomainException(
    code = "account.email.not_found",
    message = "이메일이 존재하지 않습니다.",
    data = null,
)