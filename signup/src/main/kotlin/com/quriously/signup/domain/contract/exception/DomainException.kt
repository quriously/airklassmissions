package com.quriously.signup.domain.contract.exception

import java.lang.Exception

open class DomainException(
    val code: String?,
    val data: Map<String, Any?>?,
    message: String?,
    cause: Throwable? = null,
) : Exception(message, cause)