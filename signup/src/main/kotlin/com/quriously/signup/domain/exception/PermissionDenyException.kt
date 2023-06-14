package com.quriously.signup.domain.exception

import com.quriously.signup.domain.contract.exception.DomainException

class PermissionDenyException: DomainException(
    code = "account.permission.deny",
    message = "권한이 없습니다.",
    data = null,
)