package com.quriously.signup.domain.exception

import com.quriously.signup.domain.contract.exception.DomainException

class RequireTermException: DomainException(
    code = "account.term.require",
    data = null,
    message = "약관에 동의해주세요.",
)