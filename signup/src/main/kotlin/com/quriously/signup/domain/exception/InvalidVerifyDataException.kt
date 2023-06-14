package com.quriously.signup.domain.exception

import com.quriously.signup.domain.contract.exception.DomainException

class InvalidVerifyDataException : DomainException(
    code = "account.verify.invalid",
    message = "올바른 데이터를 입력해주세요.",
    data = null,
)